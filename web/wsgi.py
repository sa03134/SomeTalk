#!/usr/bin/python3.6m
import sys
sys.path.insert(0, '/var/www/')

from web import app as application
application.secret_key = b'1234wqerasdfzxcv'