import kotlinx.serialization.Serializable

@Serializable
data class TimeResponse(
    val mti: Int,
    val time: String
)