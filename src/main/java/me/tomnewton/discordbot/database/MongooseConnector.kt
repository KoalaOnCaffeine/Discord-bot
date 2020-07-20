package me.tomnewton.discordbot.database

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class MongooseConnector(private val username: String, private val password: String, private val databaseName: String) : DatabaseConnector {

    private lateinit var client: MongoClient

    override fun connect() {
        val uri = MongoClientURI("mongodb+srv://$username:$password@database-xjonx.gcp.mongodb.net/$databaseName?retryWrites=true&w=majority")
        client = MongoClient(uri)
    }

    override fun getDatabase(name: String): MongoDatabase = client.getDatabase(name)

    fun getCollection(name: String): MongoCollection<Document> = name.split(".").let {
        if (it.size != 2) throw IllegalArgumentException("Provided argument '$name' does not match the format Database.Collection!")
        return getDatabase(it[0]).getCollection(it[1])
    }

}