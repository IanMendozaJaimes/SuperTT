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
startLinkScript(){
	echo "starting integration scripts..."
	cd Integration_scripts
	python main.py
	cd ..
}
startNNModel(){
	echo "starting Neural network model..."
	cd nn
	python test_model.py $1
	cd ..
}
restartPSQL
startLinkScript &
startNNModel $1 &
startDjango $1

