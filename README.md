# task-4

1. Create secrets.properties:
To run the application, you need to create a secrets.properties file in the resources directory with the following 
content, replacing the placeholders with your own credentials:

```properties
DB_URL=jdbc:postgresql://{DB_IP_ADDRESS}:{DB_PORT}/{DB_TABLE}
DB_USERNAME={DB_USERNAME}
DB_PASSWORD={DB_PASSWORD}
```

2. Database Setup:
Create a table in the database according to the script `src/main/java/org/example/sql/db.sql`

