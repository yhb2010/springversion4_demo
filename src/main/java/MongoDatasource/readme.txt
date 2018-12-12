使用jndi mongodb数据源

Main changes
v3.0 now returns com.mongodb.client.MongoDatabase instead of com.mongodb.DB (deprecated)
v2.0 uses MongoDB java client v3.0.0
v1.0 uses MongoDB java client v2.12.2

<Resource name="testMongodbDS"
    auth="Container"
    type="com.mongodb.client.MongoDatabase"
    factory="org.mongodb.datasource.MongoDatasourceFactory"

    host="localhost"
    port="27017"

    databaseName="test"
    username=""
    password=""

    minPoolSize="10"
    maxPoolSize="100"
    maxWaitTime="10000"
/>

// Retrieve connection from datasource
Context initCtx = new InitialContext();
MongoDatabase db = (MongoDatabase) initCtx.lookup("java:/comp/env/testMongodbDS");
// Perform a query
db.getCollectionNames();