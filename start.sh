#!/bin/bash

startDjango(){
	echo "starting Django..."
	echo $1
	python supertt/manage.py runserver $1
}
restartPSQL(){
	echo "starting PostgreSQL"
	sudo systemctl restart postgresql.service
}

startDB_Checker(){
	echo "starting DB checking for new files..."
	python main_db.py
}

restartPSQL
startDB_Checker &
startDjango $1
