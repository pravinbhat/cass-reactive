# cass-reactive
A sample Cassandra reactive application using DataStax Java Driver, Reactive Spring and Spring Boot.

The app implements reactive endpoints performing CRUD operations.

The app shows the recommended way (https://github.com/pravinbhat/cass-reactive/blob/main/src/main/java/com/bhatman/learn/cass/reactive/product/ProductController.java) to perfrom bulk-updates (see /bulk_update endpoint) in Cassandra using the Reactive stack.

The app also implements pagination functionality (https://github.com/pravinbhat/cass-reactive/blob/main/src/main/java/com/bhatman/learn/cass/reactive/product/ProductPagingDao.java) using the DataStax java-driver features.
