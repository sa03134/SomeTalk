from flask import Flask, render_template, request, session, redirect, url_for
from datetime import datetime

import base64, pymysql, os, pickle

app = Flask(__name__)
app.secret_key = b'1234wqerasdfzxcv'

@app.route("/")
def index_page() :
	if 'name' in session : return render_template("index.html", session=session)
	return render_template("index.html")

@app.route("/board")
def board_page() :
	db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
	cursor = db.cursor(pymysql.cursors.DictCursor)

	sql= """SELECT * FROM board order by No desc"""
	cursor.execute(sql)
	param = cursor.fetchall()
	db.close()
	return render_template("board.html", posts=param)

@app.route("/login")
def login_page() :
	if 'name' in session : return redirect(url_for('index_page')) 
	return render_template("login.html")

@app.route("/WritePost", methods=['GET', 'POST'])
def writepost_page() :
	if request.method == "GET" : 
		if 'name' in session : return render_template("writepost.html")
		return redirect(url_for('login_page'))
	else :
		if 'name' in session : 
			#return session['name']
			db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
	
			cursor = db.cursor()
			TITLE = request.form['Title']
			CONTENT = request.form['Content']
			now = datetime.now()
			DATETIME = str(now.year) + "-" + str(now.month) + "-" + str(now.day) + " " + str(now.hour) + ":" + str(now.minute)
			sql = """INSERT INTO board(Author, Title, Content, DATE) VALUES("{0}", "{1}", "{2}", "{3}")""".format(session['name'], TITLE, CONTENT, DATETIME)
			cursor.execute(sql)

			db.commit()
			db.close()
			return redirect(url_for('board_page'))
		else :
			return redirect(url_for('login_page'))

@app.route("/ViewPost")
def viewpost_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('board_page'))
		
		db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
		
		cursor = db.cursor(pymysql.cursors.DictCursor)

		sql= """SELECT * FROM board where No={0}""".format(request.args.get('No')) # 
		cursor.execute(sql)
		param = cursor.fetchone()

		return render_template("viewpost.html", param=param)
	return redirect(url_for('login_page'))

@app.route("/register")
def register_page() :
	return render_template("register.html")

@app.route("/Auth", methods=['POST'])
def Auth_page() :
	ID = request.form['ID']
	PW = request.form['password']

	db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
	
	cursor = db.cursor()

	sql= """SELECT * FROM user where ID="{0}" and PW="{1}" """.format(ID, PW)
	cursor.execute(sql)
	param = cursor.fetchone()
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
	COMT = request.form['Comment']

	db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
	
	cursor = db.cursor()

	sql= """SELECT * FROM user where ID="{0}" """.format(ID)
	cursor.execute(sql)
	param = cursor.fetchone()

	if type(param) == type(None) :
		sql = """INSERT INTO user VALUES("{0}", "{1}", "{2}", "{3}")""".format(ID, PW, NICK, COMT)
		cursor.execute(sql)

		sql = """INSERT INTO user_web VALUES("{0}", "")""".format(ID)
		cursor.execute(sql)
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
		db = pymysql.connect(host='localhost',
	                     port=3306,
	                     user='root',
	                     passwd='trainer',
	                     db='qerogram',
	                     charset='utf8')
		
		cursor = db.cursor(pymysql.cursors.DictCursor)

		sql= """SELECT * FROM user where ID="{0}" """.format(session['name'])
		cursor.execute(sql)
		param = cursor.fetchone()


		sql= """SELECT Data FROM user_web where ID="{0}" """.format(session['name'])
		cursor.execute(sql)
		user_web = cursor.fetchone()

		if user_web['Data'] != "" :
			user_web = pickle.loads(base64.b64decode(user_web['Data']))

		db.close()
		try :
			return render_template("profile.html", session=session, user=param, web=user_web['web'])
		except :
			return render_template("profile.html", session=session, user=param, web="")

	return redirect(url_for('index_page'))

@app.route("/DeletePost", methods=["GET"])
def delete_post_page() :
	if 'name' in session : 
		if request.args.get('No') == "" or request.args.get('No') == None : return redirect(url_for('viewpost_page'))
		
		db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
		
		cursor = db.cursor(pymysql.cursors.DictCursor)

		sql= """SELECT * FROM board where No={0}""".format(request.args.get('No')) # 
		cursor.execute(sql)
		param = cursor.fetchone()

		if param['Author'] == session['name'] :
			sql= """DELETE FROM board where No={0}""".format(request.args.get('No')) # 
			cursor.execute(sql)
			db.commit()
		db.close()
		
	return redirect(url_for('board_page'))

@app.route("/Edit_Profile", methods=["GET", "POST"])
def Edit_Profile_page() :

	web = request.form['web']

	info = {}
	for _ in ['web', 'ID', 'base64']:
		info[_] = request.form.get(_, '')

	db = pymysql.connect(host='localhost',
                     port=3306,
                     user='root',
                     passwd='trainer',
                     db='qerogram',
                     charset='utf8')
	
	cursor = db.cursor()
	
	DATA = base64.b64encode(pickle.dumps(info))

	sql = """UPDATE user_web SET Data="{0}" WHERE ID="{1}" """.format(str(DATA)[2:-1], session['name'])

	if info['base64'] == "1" : 
		DATA = info['web']
		sql = """UPDATE user_web SET Data="{0}" WHERE ID="{1}" """.format(DATA, session['name'])

	cursor.execute(sql)
	db.commit()
	db.close()

	return redirect(url_for('Profile_page'))

#app.run(debug=True, threaded=True, host="0.0.0.0", port=5000)