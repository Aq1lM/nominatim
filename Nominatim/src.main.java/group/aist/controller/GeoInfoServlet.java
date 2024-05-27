package group.aist.controller;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import group.aist.business.GeoInfoService;
import group.aist.business.impl.GeoInfoServiceImpl;
import group.aist.dataAccess.impl.JdbcGeoInfo;
import group.aist.dto.AddGeoInfoRequest;
import group.aist.mapper.impl.GeoInfoMapperImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author AqilM
 * 
 * Servlet class containing business logic for Web Service
 */

@WebServlet(urlPatterns = { "/search", "/reverse" })
public class GeoInfoServlet extends HttpServlet {

	private final String baseUrl = "https://nominatim.md7.info/";
	private final GeoInfoService geoInfoService;

	public GeoInfoServlet() {
		this.geoInfoService = new GeoInfoServiceImpl(new GeoInfoMapperImpl(), new JdbcGeoInfo());
	}

	private static final long serialVersionUID = 1L;

	/**
	 * doGet() handles GET request
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String getPath = request.getServletPath();
		String url = "";

		if (getPath.equals("/search")) {
			url = searchPath(request, response);
		} else if (getPath.equals("/reverse")) {
			url = reversePath(request, response);
		} else {
			return;
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(url);

			try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
				String json = EntityUtils.toString(httpResponse.getEntity());

				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(json);

				response.setContentType("application/json");
				response.getWriter().write(rootNode.toString());

				AddGeoInfoRequest addGeoInfoRequest = createGeoDto(rootNode);
				add(addGeoInfoRequest);
			}
		}
	}

	/**
	 * configure search URL
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return String
	 * @throws IOException
	 */
	private String searchPath(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String url;
		String address = request.getParameter("address");
		boolean addressCheck = address == null || address.isEmpty();
		if (addressCheck) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address parameter is missing");
		}
		url = baseUrl + "search?q=" + address + "&format=jsonv2";

		return url;
	}

	/**
	 * configure reverse URL
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return String
	 * @throws IOException
	 */
	private String reversePath(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String url;
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		boolean checkLatLon = lat == null || lat.isEmpty() || lon == null || lon.isEmpty();

		if (checkLatLon) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address parameter is missing");
		}
		url = baseUrl + "reverse?lat=" + lat + "&lon=" + lon + "&format=jsonv2";

		return url;
	}

	/**
	 * create AddGeoInfoRequest object according to JSON
	 * 
	 * @param JsonNode
	 * @return AddGeoInfoRequest
	 */
	private AddGeoInfoRequest createGeoDto(JsonNode jsonNode) {

		AddGeoInfoRequest addGeoInfoRequest = new AddGeoInfoRequest();
		addGeoInfoRequest.setPlaceId(jsonNode.path("place_id").asInt());
		addGeoInfoRequest.setLat(jsonNode.path("lat").asText());
		addGeoInfoRequest.setLon(jsonNode.path("lon").asText());
		addGeoInfoRequest.setDisplayName(jsonNode.path("display_name").asText());
		addGeoInfoRequest.setCategory(jsonNode.path("category").asText());

		return addGeoInfoRequest;
	}

	/**
	 * pass AddGeoInfoRequest to business layer
	 * 
	 * @param AddGeoInfoRequest
	 */
	private void add(AddGeoInfoRequest addGeoInfoRequest) {
		this.geoInfoService.add(addGeoInfoRequest);
	}

}
