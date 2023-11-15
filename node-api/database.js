import express from 'express';
import mysql from 'mysql2';
import dotenv from 'dotenv';
dotenv.config();
const pool = mysql.createPool({
    host: process.env.MYSQL_HOST,
    user: process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD,
    database: process.env.MYSQL_DATABASE
}).promise()

export async function getFabric(){
    const[rows] = await pool.query("SELECT * FROM Fabric_information")
    return rows
}


const result = await pool.query("SELECT * FROM Fabric_information")
const rows = result[0]
console.log(rows)


const result2 = await pool.query("SELECT * FROM Defect_information")
const rows2 = result2[0]
console.log(rows2)


