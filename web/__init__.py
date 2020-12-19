from flask import Flask, render_template, request, session, redirect, url_for
from datetime import datetime

import base64, pymysql, os
import binascii

app = Flask(__name__)
app.secret_key = os.urandom(16)

def getDB() :
	db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='sometalk',
                     charset='utf8')
	return db

def getTime() :
	now = datetime.now()
	DATETIME = f"{now.year:04d}-{now.month:02d}-{now.day:02d} {now.hour:02d}:{now.minute:02d}:{now.second:02d}"
	return DATETIME

def executeQuery(db, cursor_type, query, all=None) :
	Injection_Filter = {"'" : "%27", '"' : '\"', '#': '%23', '--' : '', "union" : "", "select": ""}
	for key, value in Injection_Filter.items() : 
		query = query.replace(key, value)
	# cursor_type == 1 : pymysql.cursors.DictCursor
	# cursor_type == 0 : NULL
	cursor = ""
	if cursor_type == 1 : cursor = db.cursor(pymysql.cursors.DictCursor)
	else : cursor = db.cursor()
	cursor.execute(query)
	param = ""
	if all != None : param = cursor.fetchall()
	else : param = cursor.fetchone()
	return param

@app.route("/")
def index_page() :
	db = getDB()
	param = executeQuery(db, 1, "SELECT * from board where postLike+postUnLike > 0 order by DATE desc", True)
	ad = executeQuery(db, 1, "SELECT * from ad")
	if 'name' in session : 
		query = executeQuery(db, 1, "SELECT * FROM user WHERE id='{0}'".format(session['name']) )
		db.close()
		return render_template("index.html", session=session, user=query, posts=param, ad=ad)
	db.close()
	return render_template("index.html", posts=param, ad=ad)

@app.route("/login")
def login_page() :
	if 'name' in session : return redirect(url_for('index_page')) 
	return render_template("login.html")

def isMentor() :
	db = getDB()
	ad = executeQuery(db, 1, f"SELECT * FROM user where id='{session['name']}'")
	db.close()

	if int(ad['perm']) > 1 : return True
	return False

@app.route("/ad")
def ad_page() :
	db = getDB()
	ad = executeQuery(db, 1, "SELECT * FROM ad")
	db.close()
	return render_template("adv.html", param=ad)

@app.route("/Mentor/DeleteAd")
def ad_delete_page() :
	if 'name' in session :
		if not isMentor() : return {"result" : False}
		db = getDB()
		query = executeQuery(db, 1, f"SELECT count(Author) as c FROM ad WHERE Author='{session['name']}'")
		if int(query['c']) != 0 :
			executeQuery(db, 0, "DELETE FROM ad")
			db.commit()
			os.remove("./static/ad.jpg")
			os.remove("./static/ad.png")
			os.remove("./static/ad.gif")
			os.system("cp ./static/back_ad.jpg ./static/ad.jpg")
		db.close()
		return {"result" : True}
	return {"result" : False}

@app.route("/Mentor/EditAd", methods=["GET", "POST"])
def ad_edit_page() :
	if request.method == "GET" :
		if 'name' in session :
			if not isMentor() : return "<script>window.location='" + request.referrer + "';</script>"
			
			db = getDB()
			query = executeQuery(db, 1, f"""SELECT * FROM mentor where id='{session['name']}' order by point desc limit 1""")

			ad = executeQuery(db, 1, "SELECT * FROM ad")

			if query['id'] == session['name'] : return render_template("mentor/manage_editAd.html", ad=ad)
	else :
		if 'name' in session :
			if not isMentor() : return "<script>window.location='"  + request.referrer + "';</script>"
			db = getDB()
			CONTENT = request.form['Content']
			executeQuery(db, 0, f"UPDATE ad SET Content='{CONTENT}'")
			db.commit()
			db.close()
			return redirect(url_for('ad_edit_page'))

	return redirect(url_for('login_page'))

@app.route("/Mentor/WriteAd", methods=["GET", "POST"])
def ad_write_page() :
	if request.method == "GET" :
		if 'name' in session :
			if not isMentor() : return "<script>window.location='" + request.referrer + "';</script>"
			
			db = getDB()
			query = executeQuery(db, 1, f"""SELECT * FROM mentor where id='{session['name']}' order by point desc limit 1""")
			db.close()

			if query['id'] == session['name'] : return render_template("mentor/manage_writeAd.html")
	else :
		if 'name' in session :
			if not isMentor() : return "<script>window.location='"  + request.referrer + "';</script>"

			db = getDB()
			CONTENT = request.form['Content']

			executeQuery(db, 0, f"""DELETE FROM ad""")
			executeQuery(db, 0, f"""INSERT INTO ad VALUES("{session['name']}", "", "{CONTENT}")""")

			uploaded_file = request.files['file']

			if uploaded_file.filename != '':
				if uploaded_file.filename.split(".")[-1].lower() == "png" or uploaded_file.filename.split(".")[-1].lower() == "jpg" or uploaded_file.filename.split(".")[-1].lower() == "jpeg" :
					uploaded_file.save("./static/tmp_ad." + uploaded_file.filename.split(".")[-1].lower())
					executeQuery(db, 1, f"""UPDATE ad SET Img='/static/ad.{uploaded_file.filename.split(".")[-1].lower()}'""")
					import base64

					output = open("./static/ad." + uploaded_file.filename.split(".")[-1].lower(), "wb")
					try :
						with open("./static/tmp_ad." + uploaded_file.filename.split(".")[-1].lower(), "r") as f :
							output.write(base64.b64decode(f.read()))
					except :
						with open("./static/tmp_ad." + uploaded_file.filename.split(".")[-1].lower(), "rb") as f :
							output.write(f.read())
							
					output.close()
					os.remove("./static/tmp_ad." + uploaded_file.filename.split(".")[-1].lower())
					# uploaded_file.save()

			db.commit()
			db.close()
			return redirect(url_for('ad_edit_page'))
	return redirect(url_for('login_page'))



@app.route("/board")
def board_page() :
	if 'name' in session :
		db = getDB()
		b_type = request.args.get('Type')
		if b_type == None or b_type == "" : b_type = "1"

		page = request.args.get("Page")
		if page == None or page == "" : page = 1
		else : page = int(page)

		user = executeQuery(db, 1, "SELECT * FROM user WHERE id='{0}'".format(session['name']) )

		# param = executeQuery(db, 1, f"""SELECT * FROM board b, category c where b.b_type=c.idx and b_type={b_type} order by No desc limit {(page - 1) * 15}, { page * 15 }""", True)
		param = executeQuery(db, 1, f"""SELECT * FROM board b, category c where b.b_type=c.idx and b_type={b_type} order by No desc limit {(page - 1) * 15}, { page * 15 }""", True)
		if int(b_type) == 0 :
			param = executeQuery(db, 1, f"SELECT * from board where postLike + postUnLike > 0 order by DATE desc limit {(page - 1) * 15}, { page * 15 }", True)
		
		db.close()
		return render_template("board.html", posts=param, b_type=b_type, user=user)
	return redirect(url_for('login_page'))

@app.route("/searchPost", methods=["POST"])
def searchpost_page() :
	if 'name' in session :
		db = getDB()
		b_type = request.form['b_type']
		Keywords = request.form['searchKey']
		try :
			param = executeQuery(db, 1, f"""SELECT * FROM board WHERE (Author LIKE '%{Keywords}%' or Title LIKE '%{Keywords}%') and b_type={b_type} order by DATE DESC""", all=True)
			user = executeQuery(db, 1, "SELECT * FROM user WHERE id='{0}'".format(session['name']) )

			db.close()
			return render_template("board.html", posts=param, b_type=b_type, user=user)
		except :
			db.close()
			pass#return render_template("board.html", posts=param, b_type=b_type)

	return redirect(url_for('board_page'))

@app.route("/WritePost", methods=['GET', 'POST'])
def writepost_page() :
	if request.method == "GET" : 
		if 'name' in session : 
			db = getDB()
			categories = executeQuery(db, 1, """SELECT * FROM category""", all=True)
			db.close()
			return render_template("writepost.html", categories=categories, posts=1 if request.args.get('Type') == "" or request.args.get("Type") == None else request.args.get('Type'))
		return redirect(url_for('login_page'))
	else :
		if 'name' in session : 
			db = getDB()
			print(request.form['Type'])
			param = executeQuery(db, 1, """SELECT * FROM board where b_type='{0}' ORDER BY No desc limit 1""".format(request.form['Type']))
			try : 
				no = int(param['No']) + 1
			except :
				no = 1
	
			TITLE = request.form['Title']
			CONTENT = request.form['Content']
			Boarder_TYPE = request.form['Type']
			
			DATETIME = getTime()

			executeQuery(db, 0, """INSERT INTO board(b_type, no, Author, Title, Content, DATE, postLike, postUnlike, accept, pic) VALUES("{4}", {5}, "{0}", "{1}", "{2}", "{3}", 0, 0, 0, "")""".format(session['name'], TITLE, CONTENT, DATETIME, Boarder_TYPE, no))

			uploaded_file = request.files['file']
			if uploaded_file.filename != '':
				if uploaded_file.filename.split(".")[-1].lower() == "png" or uploaded_file.filename.split(".")[-1].lower() == "jpg" or uploaded_file.filename.split(".")[-1].lower() == "jpeg" :
					os.system("rm -rf ./static/board_image/" + Boarder_TYPE + "_" + str(no) + "/")
					os.system("rm -rf ./static/board_image1/" + Boarder_TYPE + "_" + str(no) + "/")
					os.mkdir("./static/board_image/" + Boarder_TYPE + "_" + str(no) + "/")
					os.mkdir("./static/board_image1/" + Boarder_TYPE + "_" + str(no) + "/")
					uploaded_file.save("./static/board_image1/" + Boarder_TYPE + "_" + str(no) + "/" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower())
					executeQuery(db, 1, f"""UPDATE board SET pic="/static/board_image/{Boarder_TYPE}_{str(no)}/{session['name']}.{uploaded_file.filename.split(".")[-1].lower()}" WHERE b_type='{Boarder_TYPE}' and No='{no}'""")
					import base64
					output = open("./static/board_image/" + Boarder_TYPE + "_" + str(no) + "/" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower(), "wb")
					with open("./static/board_image1/" + Boarder_TYPE + "_" + str(no) + "/" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower(), "r") as f :
						try : 
							output.write(base64.b64decode(f.read()))
						except :
							output.write(f.read())
					output.close()
					os.remove("./static/board_image1/" + Boarder_TYPE + "_" + str(no) + "/" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower())
						
			db.commit()
			db.close()
			return redirect(url_for('board_page', Type=Boarder_TYPE))
		else :
			return redirect(url_for('login_page'))

@app.route("/writeComment", methods=['POST'])
def writecomment_page() :
	if "name" in session :
		db = getDB()
		Comment = request.form['Comment']
		b_type = request.form['Type']
		No = request.form['No']
		key = binascii.hexlify(os.urandom(16))
		executeQuery(db, 0, f"INSERT INTO comment VALUES ('{b_type}', '{No}', '{session['name']}', '{Comment}', '{getTime()}', '{str(key)[2:-1]}')")
		db.commit()
		db.close()
		return redirect(url_for("viewpost_page", Type=b_type, No=No))
	
	else : return redirect(url_for('login_page'))

@app.route("/DeleteComment", methods=['GET'])
def deletecomment_page() :
	if "name" in session :
		db = getDB()
		pkey = request.args.get("pkey")
		param = executeQuery(db, 1, f"""SELECT * FROM comment where pkey='{pkey}'""")
		if param['Author'] == session['name'] or session['name'] == "admin":
			executeQuery(db, 0, f"DELETE FROM comment where pkey='{pkey}'")
			db.commit()
		db.close()
		return redirect(url_for("viewpost_page", Type=param['b_type'], No=param['No']))
	
	else : return redirect(url_for('board_page'))


@app.route("/ViewPost")
def viewpost_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('board_page'))
		db = getDB()
		param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get('Type')))
		
		try : 
			comment_param = executeQuery(db, 1, "SELECT * FROM comment WHERE No={0} and b_type='{1}'".format(request.args.get("No"), request.args.get("Type")), all=True)
		except :
			db.close()
		
		reply_param = executeQuery(db, 1, "SELECT * FROM reply WHERE base_no={0} and base_btype='{1}' order by Accept desc, DATE desc".format(request.args.get("No"), request.args.get("Type"), session['name']), all=True)
		check_like = executeQuery(db, 1, f"""SELECT _like, _unlike from like_table where no='{request.args.get("No")}' and type='{request.args.get("Type")}'""")

		count_reply_param = executeQuery(db, 1, "SELECT Count(*) as c FROM reply WHERE base_no={0} and base_btype='{1}' and Author='{2}'".format(request.args.get("No"), request.args.get("Type"), session['name']))
		user = executeQuery(db, 1, """SELECT * FROM user where id='{0}'""".format(session['name']))
		print(count_reply_param['c']==0) and (param['Author'] != session['name'] or session['name']=="admin") and (int(user['perm']) & 2 or int(user['perm']) & 4)
		return render_template("viewpost.html", user=user, param=param, b_type=request.args.get('Type'), comments=comment_param, replys=reply_param, isWrite=(count_reply_param['c']==0) and (param['Author'] != session['name'] or session['name']=="admin") and (int(user['perm']) & 2 or int(user['perm']) & 4), check=check_like)
			
	return redirect(url_for('login_page'))

@app.route("/DeletePost", methods=["GET"])
def delete_post_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('viewpost_page'))
		db = getDB()
		param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
		
		if param['Author'] == session['name'] or session['name'] == "admin":
			executeQuery(db, 1, """DELETE FROM board where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			executeQuery(db, 1, """DELETE FROM comment where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			executeQuery(db, 1, """DELETE FROM reply where base_no={0} and base_btype='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			db.commit()
		db.close()
		if request.referrer != None:
			if "Manage/" in request.referrer :
				return {'result':'Success'}
		return redirect(url_for('board_page', Type=request.args.get("Type")))
		
	return redirect(url_for('board_page'))

@app.route("/LikePost", methods=["GET"])
def like_post() :
	if 'name' in session :
		_no = request.args.get('No')
		_type = request.args.get("Type")
		db = getDB()
		query = executeQuery(db, 1, f"SELECT * FROM like_table WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")

		if query == None:
			executeQuery(db, 1, f"INSERT INTO like_table VALUES('{_no}','{_type}','{session['name']}',1,0)")
			executeQuery(db, 1, f"UPDATE board SET postLike=(SELECT postLike from board WHERE No='{_no}' and b_type='{_type}') + 1 WHERE No='{_no}' and b_type='{_type}'")
			db.commit()
			db.close()
			return {"result": "Success"}

		else :
			if query['_like'] == 0 :
				executeQuery(db, 1, f"UPDATE like_table SET _like=1 WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")
				executeQuery(db, 1, f"UPDATE board SET postLike=(SELECT postLike from board WHERE No='{_no}' and b_type='{_type}') + 1 WHERE No='{_no}' and b_type='{_type}'")
			else :
				executeQuery(db, 1, f"UPDATE like_table SET _like=0 WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")
				executeQuery(db, 1, f"UPDATE board SET postLike=(SELECT postLike from board WHERE No='{_no}' and b_type='{_type}') - 1 WHERE No='{_no}' and b_type='{_type}'")
			db.commit()
			db.close()
			return {"result": "Success"}
		
		db.close()
	return {"result": "Fail"}

@app.route("/UnLikePost", methods=["GET"])
def unlike_post() :
	if 'name' in session :
		_no = request.args.get('No')
		_type = request.args.get("Type")
		db = getDB()
		query = executeQuery(db, 1, f"SELECT * FROM like_table WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")

		if query == None:
			executeQuery(db, 1, f"INSERT INTO like_table VALUES('{_no}','{_type}','{session['name']}',0,1)")
			executeQuery(db, 1, f"UPDATE board SET postUnlike=(SELECT postUnlike from board WHERE No='{_no}' and b_type='{_type}') + 1 WHERE No='{_no}' and b_type='{_type}'")
			db.commit()
			db.close()
			return {"result": "Success"}

		else :
			if query['_unlike'] == 0 :
				executeQuery(db, 1, f"UPDATE like_table SET _unlike=1 WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")
				executeQuery(db, 1, f"UPDATE board SET postUnlike=(SELECT postUnlike from board WHERE No='{_no}' and b_type='{_type}') + 1 WHERE No='{_no}' and b_type='{_type}'")
			else :
				executeQuery(db, 1, f"UPDATE like_table SET _unlike=0 WHERE no='{_no}' and type='{_type}' and user='{session['name']}'")
				executeQuery(db, 1, f"UPDATE board SET postUnlike=(SELECT postUnlike from board WHERE No='{_no}' and b_type='{_type}') - 1 WHERE No='{_no}' and b_type='{_type}'")
			db.commit()
			db.close()
			return {"result": "Success"}
		
		db.close()
	return {"result": "Fail"}

@app.route("/EditPost", methods=["GET", "POST"])
def edit_post_page() :
	if 'name' in session :
		if request.method == "GET" : 
			db = getDB()
			param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			db.close()
			return render_template("editpost.html", param=param, b_type=request.args.get('Type'))
		else : # update
			db = getDB()
			b_type = dict(request.form.lists())
			b_type = b_type['Type'][1] if b_type['Type'][0] == "" else b_type['Type'][0]
			param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.form['No'], b_type))
			if param['Author'] == session['name'] or session['name'] == "admin" :
				executeQuery(db, 1, """UPDATE board set Content='{0}', Title='{1}' where No={2} and b_type='{3}'""".format(request.form['Content'], request.form['Title'], request.form['No'], b_type))
				db.commit()
			
			db.close()
			return redirect(url_for("viewpost_page", Type=b_type, No=request.form['No']))

	return redirect(url_for('board_page'))

@app.route("/WriteReply", methods=['GET', 'POST'])
def writereply_page() :
	if 'name' in session : 
		db = getDB()
		param = executeQuery(db, 1, """SELECT count(*) as c FROM reply where base_btype='{0}' and base_no='{1}' and Author='{2}' limit 1""".format(request.args.get('Type'), request.args.get('No'), session['name']))
		if int(param['c']) == 0 : # no duplicate
			param = executeQuery(db, 0, f"""INSERT INTO reply VALUES('{request.args.get("Type")}', '{request.args.get("No")}', '{session['name']}', '{request.args.get("Content")}', 0, '{getTime()}')""")
			executeQuery(db, 0, f"""UPDATE mentor SET point=(SELECT point + 200 from mentor where id='{session['name']}') where id='{session['name']}'""")
			db.commit()
			db.close()
			return {"result":"success"}
		db.close()
	return {"result":"fail"}

@app.route("/DeleteReply", methods=["GET"])
def delete_reply_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('viewpost_page'))
		db = getDB()
		param = executeQuery(db, 1, """SELECT * FROM reply where base_no={0} and base_btype='{1}'""".format(request.args.get('No'), request.args.get("Type")))
		
		if param['Author'] == session['name'] or session['name'] == "admin":
			executeQuery(db, 1, """DELETE FROM reply where base_no={0} and base_btype='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			db.commit()
			db.close()
			return {'result':'Success'}
		db.close()

	return {'result':'Fail'}

@app.route("/AcceptReply", methods=["GET"])
def accept_reply_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('viewpost_page'))
		db = getDB()
		boarder_user = executeQuery(db, 1, """SELECT Author, accept FROM board where No='{0}' and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
		
		if (boarder_user['Author'] == session['name'] or session['name'] == "admin") and boarder_user['accept'] == 0:
			executeQuery(db, 1, f"""UPDATE board set accept=1 WHERE no='{request.args.get("No")}' and b_type='{request.args.get("Type")}'""")
			executeQuery(db, 1, f"""UPDATE reply set accept=1 WHERE base_no='{request.args.get("No")}' and base_btype='{request.args.get("Type")}' and Author='{request.args.get('Author')}'""")
			executeQuery(db, 1, f"""UPDATE mentor set point=(SELECT point + 800 from mentor where id='{request.args.get('Author')}') where id='{request.args.get('Author')}'""")
			db.commit()
			db.close()
			return {'result':'Success'}
		db.close()

	return {'result':'Fail'}

@app.route("/register")
def register_page() :
	return render_template("register.html")

@app.route("/Auth", methods=['POST'])
def Auth_page() :
	ID = request.form['ID']
	PW = request.form['password']

	db = getDB()
	param = executeQuery(db, 1, """SELECT * FROM user where ID="{0}" and PW="{1}" """.format(ID, PW))
	db.close()

	if type(param) == type(None) :
		return render_template("login.html", Message="ID 또는 Password가 틀립니다.")

	session['name'] = ID
	return redirect(url_for('index_page'))

@app.route("/reg_Auth", methods=['POST'])
def regAuth_page() :
	ID = request.form['ID']
	PW = request.form['password']
	NICK = request.form['Nickname']
	EMail = request.form['Email']
	EMail = EMail.replace("\n", "").replace(" ", "")
	print(EMail)

	db = getDB()
	param = executeQuery(db, 0, """SELECT * FROM user where ID="{0}" """.format(ID))

	if type(param) == type(None) :
		sql = """INSERT INTO user(id, pw, nick, email, perm, pic) VALUES("{0}", "{1}", "{2}", "{3}", "1", "")""".format(ID, PW, NICK, EMail)
		executeQuery(db, 0, sql)
		db.commit()
		db.close()
		return redirect(url_for('login_page'))

	return redirect(url_for('register_page'))

@app.route("/Logout")
def logout_page() :
	session.pop('name', None)
	return redirect(url_for('index_page'))

@app.route("/Profile")
def Profile_page() :
	if 'name' in session : 
		db = getDB()
		cursor = db.cursor(pymysql.cursors.DictCursor)

		sql= """SELECT * FROM user where ID="{0}" """.format(session['name'])
		cursor.execute(sql)
		param = cursor.fetchone()

		perm = "일반 사용자"
		if int(param['perm']) & 2 :
			perm = "멘토 계정"
	
		elif int(param['perm']) & 4 :
			perm = "관리자 계정"
		
		sql = """SELECT * FROM board, category where b_type=idx and Author="{0}" ORDER BY DATE desc""".format(session["name"])
		cursor.execute(sql)
		b_param = cursor.fetchall()

		sql = """SELECT * FROM comment, category where b_type=idx and Author="{0}" ORDER BY DATE desc""".format(session["name"])
		cursor.execute(sql)
		c_param = cursor.fetchall()

		return render_template("profile.html", session=session, user=param, perm=perm, posts=b_param, comments=c_param)

	return redirect(url_for('index_page'))

@app.route('/UploadProfile', methods=['POST'])
def upload_Profile_page():
	if "name" in session :
		uploaded_file = request.files['file']

		if uploaded_file.filename != '':
			if uploaded_file.filename.split(".")[-1].lower() == "png" or uploaded_file.filename.split(".")[-1].lower() == "jpg" or uploaded_file.filename.split(".")[-1].lower() == "jpeg" :
				uploaded_file.save("./static/userprofile/tmp" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower())#uploaded_file.filename)
				db = getDB()
				executeQuery(db, 1, f"""UPDATE user SET pic="./static/userprofile/{session['name']}.{uploaded_file.filename.split(".")[-1].lower()}" WHERE id="{session["name"]}" """)
				db.commit()
				db.close()
				output = open("./static/userprofile/" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower(), "wb")
				
				with open("./static/userprofile/tmp" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower(), "r") as f :
					import base64
					try :
						output.write(base64.b64decode(f.read()))
					except:
						output.write(f.read())
					output.close()
					
					os.remove("./static/userprofile/tmp" + session['name'] + "." + uploaded_file.filename.split(".")[-1].lower())
				
				
	if request.referrer != None :
		return "<script>window.location='" + request.referrer + "';</script>"
	else :
		return "<script>window.location='/';</script>"

@app.route("/Edit_Profile", methods=["GET", "POST"])
def Edit_Profile_page() :
	if "name" in session :
		info = {}
		db = getDB()
		param = executeQuery(db, 1, """SELECT * FROM user where id="{0}" """.format(request.form.get('Id')))

		info["Password"] = request.form.get("Password")
		info["Nickname"] = request.form.get("Nickname")
		info["Email"] = request.form.get("Email").replace("\n", "")

		if request.referrer != None :
			if "Manage/user" in request.referrer :
				info["Perm"] = request.form.get("Perm").replace("\n", "")
		
		# print(param['id'])
		print(info)

		if session['name'] == "admin" or param['id'] == request.form.get('Id') :
			if len(info) == 4 :
				executeQuery(db, 0, """UPDATE user SET perm="{0}", pw="{1}", nick="{2}", email="{3}" WHERE id="{4}" """.format(info['Perm'], info['Password'], info['Nickname'], info['Email'], param['id']))
			else :
				executeQuery(db, 0, """UPDATE user SET pw="{0}", nick="{1}", email="{2}" WHERE id="{3}" """.format(info['Password'], info['Nickname'], info['Email'], param['id']))
			db.commit()
		db.close()
		
		if request.referrer != None :
			if "Manage/user" in request.referrer :
				return "<script>window.location='" + request.referrer + "';</script>"

		return redirect(url_for('Profile_page'))
	else :
		return redirect(url_for('login_page'))


### mentor function ###
def checkMentor() :
	db = getDB()
	param = executeQuery(db, 1, """SELECT * FROM user where (perm='2' or perm='4') and id='{0}'""".format(session['name']))
	db.close()
	try :
		return (int(param['perm']) & 2) or (int(param['perm']) & 4)# and session['name'] == 'admin'
	except : 
		return False

@app.route("/Mentor", methods=["GET"])
@app.route("/Mentor/", methods=["GET"])
def Mentor_page() :
	if "name" in session :
		db = getDB()
		param = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
		db.close()
		return render_template("mentor/index.html", user=param)

	return redirect(url_for('index_page'))

@app.route("/Mentor/Rank")
def Rank_page() :
	if "name" in session :
		db = getDB()
		param = executeQuery(db, 1, f"SELECT Author, count(Author) as cauthor, sum(Accept) as caccept FROM reply group by Author limit 10", all=True)
		user = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
		db.close()
		return render_template("mentor/rank.html", user=user, param=param)
	return redirect(url_for('index_page'))

@app.route("/Mentor/Manage")
def Mentor_Management_page() :
	if "name" in session :
		if checkMentor() :
			db = getDB()
			cursor = db.cursor(pymysql.cursors.DictCursor)

			sql= """SELECT * FROM user where ID="{0}" """.format(session['name'])
			cursor.execute(sql)
			param = cursor.fetchone()

			perm = "일반 사용자"
			if int(param['perm']) & 2 :
				perm = "멘토 계정"
		
			elif int(param['perm']) & 4 :
				perm = "관리자 계정"
			
			sql = """SELECT * FROM reply, category where idx=base_btype and Author="{0}" ORDER BY DATE desc""".format(session["name"])
			cursor.execute(sql)
			b_param = cursor.fetchall()

			sql = """SELECT * FROM comment where Author="{0}" ORDER BY DATE desc""".format(session["name"])
			cursor.execute(sql)
			c_param = cursor.fetchall()

			point = executeQuery(db, 1, f"""SELECT * FROM mentor where id='{session['name']}'""")
			# point = 0
			# for element in b_param : 
				# if element['Accept'] == 1 : point += 1000
				# point += 200
			
			
			req_mentor = executeQuery(db, 1, f"SELECT * from req_mentor WHERE result=0 order by DATE desc", all=True)
			
			isPossibleAd = executeQuery(db, 1, f"SELECT count(Author) as c from ad WHERE Author='{session['name']}'")

			return render_template("mentor/manage.html", session=session, user=param, perm=perm, posts=b_param, comments=c_param, point=point, req_mentor=req_mentor, isPossibleAd=isPossibleAd)
		
	return redirect(url_for("index_page"))

@app.route('/Mentor/acceptMentorPerm')
def Mentor_acceptperm_page() :
	if "name" in session :
		db = getDB()
		user_id = request.args.get("id")
		req = request.args.get("req")
		if int(req) == 1 :
			executeQuery(db, 0, f"UPDATE req_mentor SET result=1 WHERE id='{user_id}'")
			executeQuery(db, 0, f"UPDATE user SET perm=2 WHERE id='{user_id}'")
			executeQuery(db, 0, f"INSERT INTO mentor values('{user_id}', 0)")
			db.commit()
			db.close()
			return {"result": "Success"}
		else :
			executeQuery(db, 0, f"UPDATE req_mentor SET result=-1 WHERE id='{user_id}'")
			db.commit()
			db.close()
		db.close()
		return {"result": "Fail"}
	return {"result": "Fail"}


@app.route('/Mentor/requestMentorPerm')
def Mentor_requestperm_page() :
	if "name" in session :
		db = getDB()
		user = executeQuery(db, 1, f"SELECT * FROM user WHERE ID='{session['name']}'")
		if user['perm'] == '1' :
			executeQuery(db, 0, f"""INSERT INTO req_mentor VALUES('{session['name']}' , '{getTime()}', 0, '{request.args.get("ans1")}', '{request.args.get("ans2")}')""")
			db.commit()
			db.close() 
			return {"result": "success"}
		db.close()
		
	return {"result": "Fail"}

#### admin function ####
def checkAdmin() :
	db = getDB() 
	param = executeQuery(db, 1, """SELECT * FROM user where perm='4' and id='{0}'""".format(session['name']))
	db.close()
	try :
		return int(param['perm']) & 4
	except : 
		return False


@app.route("/Manage", methods=["GET"])
@app.route("/Manage/", methods=["GET"])
def Management_page() :
	if "name" in session :
		if checkAdmin() : 
			db = getDB()
			user = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
			user_result = executeQuery(db, 1, "SELECT count(*) as count from user");
			board_result = executeQuery(db, 1, "SELECT count(*) as count from board");
			accept_result = executeQuery(db, 1, "SELECT sum(Accept) as count from board");
			post_result = executeQuery(db, 1, "SELECT * from board where postLike+postUnLike>0 order by DATE desc", True)
			db.close()
			return render_template("admin/management.html", user=user, user_r=user_result, board=board_result, accept=accept_result, posts=post_result)

	return redirect(url_for('index_page'))
	
@app.route("/Manage/user", methods=["GET"])
def Management_User_page() :
	if "name" in session :
		if checkAdmin() : 
			db = getDB()
			param = executeQuery(db, 1, f"""SELECT * FROM user""", True)
			user = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
			db.close()
			return render_template("admin/manage_user.html", users=param, user=user)
	return redirect(url_for('index_page'))

@app.route("/Manage/user/profile", methods=["GET"])
def Management_User_Profile_page() :
	if "name" in session :
		if checkAdmin() : 
			id = request.args.get("id")
			db = getDB()
			param = executeQuery(db, 1, f"""SELECT * FROM user where id='{id}'""")
			db.close()
			print(id)
			return render_template("admin/manage_user_profile.html", user=param, perm=param['perm'])
	return redirect(url_for('index_page'))

@app.route("/Manage/board", methods=["GET"])
def Management_Board_page() :
	if "name" in session :
		if checkAdmin() : 
			db = getDB()
			param = executeQuery(db, 1, f"""SELECT * FROM board, category where b_type=idx order by DATE desc""", True)
			user = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
			db.close()
			return render_template("admin/manage_board.html", posts=param, user=user)
	return redirect(url_for('index_page'))


@app.route("/Manage/EditPost", methods=["GET", "POST"])
def Management_edit_post_page() :
	if 'name' in session :
		if request.method == "GET" : 
			db = getDB()
			param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.args.get('No'), request.args.get("Type")))
			user = executeQuery(db, 1, "SELECT * FROM user where id='{0}'".format(session['name']))
			db.close()
			return render_template("admin/manage_editpost.html", param=param, b_type=request.args.get('Type'), user=user)
		else : # update
			db = getDB()
			b_type = dict(request.form.lists())
			print(b_type)
			b_type = b_type['Type'][1] if b_type['Type'][0] == "" else b_type['Type'][0]
			print(b_type)
			param = executeQuery(db, 1, """SELECT * FROM board where No={0} and b_type='{1}'""".format(request.form['No'].replace("\n", ""), b_type))
			if param['Author'] == session['name'] or session['name'] == "admin" :
				print("""UPDATE board set Content='{0}', Title='{1}' where No={2} and b_type='{3}'""".format(request.form['Content'], request.form['Title'], request.form['No'].replace("\n", ""), b_type))
				executeQuery(db, 1, """UPDATE board set Content='{0}', Title='{1}' where No={2} and b_type='{3}'""".format(request.form['Content'], request.form['Title'], request.form['No'].replace("\n", ""), b_type))
				db.commit()
			
			db.close()
			if "Manage/" in request.referrer :
				return "<script>window.location='" + request.referrer + "';</script>"
			#return redirect(url_for("viewpost_page", Type=b_type, No=request.form['No']))

	return redirect(url_for('board_page'))


app.run(debug=True, threaded=True, host="0.0.0.0", port=80)