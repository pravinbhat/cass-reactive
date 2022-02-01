# cass-reactive

A sample Cassandra reactive application using DataStax Java Driver, Reactive Spring and Spring Boot.
The app implements reactive endpoints performing CRUD operations.

### Bulk-Update Key Points

- The app shows the recommended way (https://github.com/pravinbhat/cass-reactive/blob/main/src/main/java/com/bhatman/learn/cass/reactive/product/ProductController.java) to perfrom bulk-updates (see /bulk_update endpoint) in Cassandra using the Reactive stack.
- Bulk updates to multiple rows using the “in” clause with a list of primary-keys would update multiple partitions (in most scenarios) and this is not an efficient way to use Cassandra.
- Cassandra works the best when a query (read/write) can be targeted to a single partition. If bulk operations that target multiple partitions are needed, it is best done using parallel operations with each operation hitting a single partition. See the parallel-stream solution using the “/bulk_update” endpoint.

Note: The DAO class used the “NullSavingStrategy.DO_NOT_SET” property provided by the DataStax driver for performing the update operations. This option helps make the bulk-update operation efficient as it does not need a read-ahead call for every record and updates only the applicable fields (does not write the whole POJO).

### Pagination Key Points

- For pagination functionality using the DataStax java-driver see https://github.com/pravinbhat/cass-reactive/blob/main/src/main/java/com/bhatman/learn/cass/reactive/product/ProductPagingDao.java
- For addtional details on pagination using DataStax driver and asynchronous paging see https://docs.datastax.com/en/developer/java-driver/4.13/manual/core/paging/#asynchronous-paging

Note: POSTMAN collection to test/verify endpoints on this app can be found here https://github.com/pravinbhat/cass-reactive/blob/main/src/main/resources/cass-reactive.postman_collection.json
