CREATE KEYSPACE learn WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE learn.product (
    prod_id uuid,
    created timestamp,
    location text,
    name text,
    price float,
    type text static,
    PRIMARY KEY (prod_id, created)
) WITH CLUSTERING ORDER BY (created ASC)
    AND additional_write_policy = '99PERCENTILE'
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND nodesync = {'enabled': 'true', 'incremental': 'true'}
    AND read_repair = 'BLOCKING'
    AND speculative_retry = '99PERCENTILE';

COPY product (prod_id, name, created, type, location, price) 
FROM '<add_path>/products.csv' 
WITH HEADER = TRUE AND DELIMITER = ',' AND DATETIMEFORMAT='%Y/%m/%d';