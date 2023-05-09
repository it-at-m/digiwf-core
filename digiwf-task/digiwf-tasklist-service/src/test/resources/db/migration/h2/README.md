# How to get this scripts

Instead of creating H2 Scripts, we put the h2 into PostgreSQL mode and add parameter `NON_KEYWORDS=value` to its JDBC
init string. So just copy the `src/main/resources/db/migration/postgres/` content to this directory ony any changes 
in the future.