from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

IP_ADDRESS = "localhost"
DB_NAME = "tt_database"
PORT = 5432
USERNAME = "postgres"
PASSWORD = "postgres"

CONFIG_STRING = f"postgresql://{USERNAME}:{PASSWORD}@{IP_ADDRESS}:{PORT}/{DB_NAME}"

engine = create_engine(CONFIG_STRING, echo=True)
Session = sessionmaker(bind=engine)

Base = declarative_base()