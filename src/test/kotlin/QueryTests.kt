import kdbc.execute
import models.Customer
import models.SelectCustomer
import models.UpdateCustomer
import org.h2.jdbcx.JdbcDataSource
import org.junit.Assert.assertEquals
import org.junit.Test

class QueryTests {
    private val db = JdbcDataSource().apply {
        setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
        execute("CREATE TABLE customer (id integer not null primary key auto_increment, name text)")
        execute("INSERT INTO customer (name) VALUES ('John')")
        execute("INSERT INTO customer (name) VALUES ('Jill')")
    }

    @Test
    fun queryTest() {
        val john = SelectCustomer(db).byId(1)
        assertEquals(1, john.id)
        assertEquals("John", john.name)
    }
//
    @Test
    fun updateTest() {
        UpdateCustomer(Customer(1, "Johnnie")).execute(db)

        val updatedName = SelectCustomer(db).byId(1).name

        assertEquals("Johnnie", updatedName)
    }
//
//    @Test
//    fun insertTest() {
//        val c = Customer(name = "Jane")
//
//        db.execute {
//            generatedKeys { c.id = asInt }
//            "INSERT INTO customers (name) VALUES (${c.name()})"
//        }
//
//        val jane = getCustomerById(c.id!!)
//
//        assertEquals("Customer(id=${c.id}, name=Jane)", jane.toString())
//    }
//
//    @Test(expected = SQLException::class)
//    fun deleteTest() {
//        val id = 1
//        db.execute { "DELETE FROM customers WHERE id = ${id.p}" }
//        getCustomerById(1)
//    }
//
//    @Test
//    fun resultSetTest() {
//        db.query {
//            "SELECT * FROM customers"
//        } execute {
//            while (resultSet.next()) println(resultSet.getString("name"))
//        }
//    }
//
//    @Test
//    fun sequenceTest() {
//        val seq = db.query { "SELECT * FROM customers" } sequence { Customer(this) }
//        seq.forEach {
//            println("Found customer in seq: $it")
//        }
//    }
//
//    @Test
//    fun transaction_rollback() {
//        try {
//            db.transaction {
//                execute { "UPDATE customers SET name = 'Blah' WHERE id = 1" }
//                execute { "SELECT i_will_fail" }
//            }
//        } catch (ex: SQLException) {
//            // Expected
//            println("As expected")
//        }
//        val customer = db.query { "SELECT * FROM customers WHERE id = 1" } single { Customer(this) }
//        Assert.assertNotEquals("Name should not be changed", "Blah", customer.name)
//    }

}