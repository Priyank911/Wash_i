data class Order(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val pickupLocation: String = "",
    val clothesCount: Int = 0,
    val service: String = "",
    val urgent: Boolean = false
)
data class UserSchema(
    val serviceProviders: Any? = null,
    val pincode: String = "",
    val shopAddress: String = "",
    val name: String = "",
    val firstName: String = "",
    val userType: String = "",
    val user: User = User(),
    val serviceProvider: ServiceProvider? = null,
    val customer: Customer? = null
)

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String? = null,
    val email: String? = null,
    val password: String = "",
    val userType: String = "Customer"
)

data class ServiceProvider(
    val name: String = "",
    val shopAddress: String = "",
    val pincode: Int = 0,
    val services: List<String> = listOf()
)

data class Customer(
    val location: String = "",
    val orders: List<Order>? = null
)
