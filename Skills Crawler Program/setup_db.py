from sql_database import *

output_table = 'result'

create_table_query = f"""CREATE TABLE {output_table} (
    id INT NOT NULL AUTO_INCREMENT,
    skill_name varchar(255),
    developer_type varchar(255),
    developer_name varchar(255),
    skill_corpus varchar(255),
    skill_description varchar(3000),
    PRIMARY KEY(id)
    )
     """

connection = create_server_connection()
create_database(connection, f'CREATE DATABASE {DB_NAME}')
connection = create_db_connection()
execute_query(connection, create_table_query)
