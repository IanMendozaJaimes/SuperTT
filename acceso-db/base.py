from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

engine = create_engine('postgresql://postgres:postgres@localhost:5432/tt_database', echo=True)
Session = sessionmaker(bind=engine)

Base = declarative_base()