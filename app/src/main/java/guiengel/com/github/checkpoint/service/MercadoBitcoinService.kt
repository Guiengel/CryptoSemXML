package guiengel.com.github.checkpoint.service

import guiengel.com.github.checkpoint.model.TicketResponse
import retrofit2.Response
import retrofit2.http.GET

interface MercadoBitcoinService {
    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TicketResponse>
}