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
	cd Integration_scripts
	python main_db.py
	cd ..
}
startNNModel(){
	echo "starting Neural network model..."
	cd nn
	python test_model.py
	cd ..
}
restartPSQL
startDB_Checker &
startNNModel &
startDjango $1
