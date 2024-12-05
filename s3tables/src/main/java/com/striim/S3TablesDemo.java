package com.striim;

import org.apache.spark.sql.SparkSession;

public class S3TablesDemo
{
    public static void main( String[] args )
    {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .config("spark.sql.catalog.s3tablesbucket","org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.s3tablesbucket.catalog-impl","software.amazon.s3tables.iceberg.S3TablesCatalog")
                .config("spark.sql.catalog.s3tablesbucket.warehouse","<TableBucketARN>")
                .config("spark.sql.catalog.s3tablesbucket.client.region","<region>")
                .config("spark.sql.extensions","org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
                .config("spark.jars.packages","software.amazon.s3:s3-tables-catalog-for-iceberg:0.1.0")
                .getOrCreate();
        try {
            session.sql("CREATE NAMESPACE IF NOT EXISTS s3tablesbucket.store");
            session.sql("use s3tablesbucket.store");
            session.sql("CREATE TABLE IF NOT EXISTS employee( id INT, name STRING )");
            session.sql("insert into employee values (1,'kg')");
            session.sql("select * from employee").show();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
