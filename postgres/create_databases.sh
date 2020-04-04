#!/usr/bin/env bash

databases=(
	tenant1
	tenant2
	tenant3
)

for db in ${databases[*]} ; do
	echo "select 'create database ${db};' where not exists (select from pg_database where datname = '${db}')\gexec" | psql
	echo "create table models (id text primary key, created_at timestamp, tenant text)" | psql -d "${db}"
done

export admin_db=admin
echo "select 'create database ${admin_db};' where not exists (select from pg_database where datname = '${admin_db}')\gexec" | psql
echo "create table tenants (id text primary key, url text not null, username text not null, password text not null)" | psql -d "${admin_db}"
echo "insert into tenants values ('tenant1', 'jdbc:postgresql://localhost:5432/tenant1', 'postgres', 'postgres')" | psql -d "${admin_db}"
echo "insert into tenants values ('tenant2', 'jdbc:postgresql://localhost:5432/tenant2', 'postgres', 'postgres')" | psql -d "${admin_db}"



