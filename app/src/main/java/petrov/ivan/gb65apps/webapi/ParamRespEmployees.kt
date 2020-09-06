package petrov.ivan.gb65apps.webapi

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ParamRespEmployees(
    @JsonProperty("response")
    val specialities: List<ParamEmployee>
): Serializable