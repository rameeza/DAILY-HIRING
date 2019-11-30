package org.dailyhiring.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.dailyhiring.dao.EmployerRepository;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class EmployerServiceImplUsingJena implements EmployerService{
	private EmployerRepository employerRepository;
	private static RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
			.destination("http://localhost:3030/dh");

	@Autowired
	public EmployerServiceImplUsingJena(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}

	@Override
	public Employer save(Employer employer) {
		String name = employer.getName();
		String email = employer.getEmail();
		String gender = employer.getGender();
		String dateOfBirth = employer.getDateOfBirth();
		String language = employer.getLanguage();
		Double latitude = employer.getLatitude();
		Double longitude = employer.getLongitude();
		String password = employer.getPassword();
		String telephone = employer.getTelephoneNumber();
		String fax = employer.getFaxNumber();
		String buildingName = employer.getAddress().getBuildingName();
		String identifier = "http://purl.org/dh/0.1/"+employer.getEmail();
		String landmark = employer.getAddress().getLandmark();
		String streetAddress = employer.getAddress().getStreetAddress();
		String locality = employer.getAddress().getLocality();
		String state = employer.getAddress().getState();
		String countryName = employer.getAddress().getCountryName();
		String postalCode = employer.getAddress().getPostalCode();
		System.out.println(latitude);
		try (RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ){
			conn.update("PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
					+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
					+ "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>"
					+ "PREFIX dc:<http://purl.org/dc/terms/>"
					+ "PREFIX dh:<http://purl.org/dailyhire/0.1/>"
					+ "PREFIX schema:<http://schema.org/>"
					+ "PREFIX essglobal:<http://purl.org/essglobal/vocab/>"
					+ "PREFIX juso:<http://rdfs.co/juso/>" 
					
					+ "INSERT DATA { <"+identifier+"> foaf:name '"+name+"' ."
					+ " <"+identifier+">  foaf:mbox '"+email+"' ."
					+ "<"+identifier+"> foaf:gender '"+gender+"' ."
					+ " <"+identifier+">  vcard:bday '"+dateOfBirth+"'^^xsd:date ."
					+ " <"+identifier+">  dc:language '"+language+"' ."
					+ " <"+identifier+">  dh:latitude '"+latitude+"'^^xsd:decimal ."
					+ "<"+identifier+">  dh:longitude '"+longitude+"'^^xsd:decimal  ."
					+ "<"+identifier+">  schema:telephone '"+telephone+"' ."
					+ "<"+identifier+">  schema:faxNumber '"+fax+"' ."
					+ "<"+identifier+">  dh:password '"+password+"' ."
					+ "<"+identifier+">  dh:buildingName '"+buildingName+"' ."
					+ "<"+identifier+">  dh:landmark '"+landmark+"' ."
					+ "<"+identifier+">  juso:full_address '"+streetAddress+"' ."
					+ "<"+identifier+">  vcard:locality '"+locality+"' ."
					+ "<"+identifier+">  essglobal:state '"+state+"' ."
					+ "<"+identifier+">  juso:country-name '"+countryName+"' ."
					+ "<"+identifier+">  vcard:postal-code '"+postalCode+"' ."
					+ "}"
					 );
					//conn.queryResultSet("SELECT ?s ?p WHERE { ?s ?p <o:Rameez> }", ResultSetFormatter::out);
			
			}
				
				
				//employerRepository.save(employer);
		return new Employer();
	}


	public EmployerServiceImplUsingJena() {
		super();
	}

	@Override
	public Employer findById(int theId) {
		Employer ret = null;
		Optional<Employer> optionalEmployer =  employerRepository.
				findById(theId);
		if (optionalEmployer.isPresent()) {
			ret = optionalEmployer.get();
		}
		return ret;
	}

	
	public Employer findById(String emailId, HttpServletRequest request) {
		Employer ret = null;
		String identifier = "<http://purl.org/dh/0.1/"+emailId+">";
		/* // This works correctly - from here upto qe2.close() QueryExecution qe2 =
		 * QueryExecutionFactory.sparqlService(
		 * "http://localhost:3030/dh/query","SELECT * WHERE" + "{?s ?p ?o .}");
		 * ResultSet results2 = qe2.execSelect(); while (results2.hasNext() ) {
		 * QuerySolution sln = results2.nextSolution();
		 * System.out.println(">>>>>>>>>>>>>>> Hurray:"); } qe2.close();
		 * 
		 */		
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query","PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>\r\n" + 
				"PREFIX dc:<http://purl.org/dc/terms/>\r\n" + 
				"PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n" + 
				"PREFIX schema:<http://schema.org/>\r\n" + 
				"PREFIX juso:<http://rdfs.co/juso/>\r\n" + 
				"PREFIX essglobal:<http://purl.org/essglobal/vocab/>\r\n" + 
				"SELECT ?name ?email ?gender ?dateOfBirth ?language ?latitude ?longitude ?telephone ?fax ?password ?buildingName ?landmark ?streetAddress ?countryName ?postalCode\r\n" + 
				"WHERE {\r\n" 
				+identifier+" foaf:name ?name .\r\n" + 
				identifier+"  foaf:mbox ?email .\r\n" + 
				identifier+"  foaf:gender ?gender .\r\n" + 
				identifier+"  vcard:bday ?dateOfBirth .\r\n" + 
				identifier+"  dc:language ?language . \r\n" + 
				identifier+"  dh:latitude ?latitude .\r\n" + 
				identifier+"  dh:longitude ?longitude .\r\n" + 
				identifier+"  schema:telephone ?telephone .\r\n" + 
				identifier+"  schema:faxNumber ?fax .\r\n" + 
				identifier+"  dh:password ?password .\r\n" + 
				identifier+"  dh:buildingName ?buildingName .\r\n" + 
				identifier+"  dh:landmark ?landmark .\r\n" + 
				identifier+"  juso:full_address ?streetAddress .\r\n" + 
				identifier+"  juso:country-name ?countryName .\r\n" + 
				identifier+"  vcard:postal-code ?postalCode . \r\n" + 
				"}");
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext() ) {
			System.out.println(">>>>>>>>>>>>>>> QE3 Hurray:");
			QuerySolution sln = results3.nextSolution();
			String name = sln.getLiteral("name").toString();
			String email = sln.getLiteral("email").toString();
			String gender = sln.getLiteral("gender").toString();
			String dateOfBirth = sln.getLiteral("dateOfBirth").toString().replace("^^http://www.w3.org/2001/XMLSchema#date", "");
			String language = sln.getLiteral("language").toString();
			Double latitude = sln.getLiteral("latitude").getDouble();
			Double longitude = sln.getLiteral("longitude").getDouble();
			String telephoneNumber = sln.getLiteral("telephone").toString();
			String faxNumber = sln.getLiteral("fax").toString();
			String password = sln.getLiteral("password").toString();
			String buildingName = sln.getLiteral("buildingName").toString();
			String landmark = sln.getLiteral("landmark").toString();
			String streetAddress = sln.getLiteral("streetAddress").toString();
			// todo - add locality and state fields
			String countryName = sln.getLiteral("countryName").toString();
			String postalCode = sln.getLiteral("postalCode").toString();

			System.out.println(">>>>>>>>>>>>>>> Employee name:" + " " + name + " |  Employee mail:" + " " + email
					+"| Latitude: " +latitude+ "| Longitude: " +longitude );

			// when passwords match
			if (password.equals(request.getParameter("password"))) {
				ret = new Employer(latitude, longitude, name, gender, language, 
						dateOfBirth, email, faxNumber,telephoneNumber, password, 
						buildingName, landmark, streetAddress, countryName, postalCode);
				request.getSession().setAttribute("employer", ret);
				
			}
			
		} qe3.close();

		
		//System.out.println(">>>>>>>>>>>>> Before ret | Employee name:"  + ret.getName());
		return ret;
	}


	public boolean isAuthenticEmployer(@Valid Employer employer) {
		String email = employer.getEmail();
		String password = employer.getPassword();
		String identifier = "http://purl.org/dh/0.1/"+employer.getEmail();
		boolean ret = false;
		
		try (RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ){
		//conn.queryResultSet("SELECT ?s WHERE { ?s <http://xmlns.com/foaf/0.1/mbox> '"+email+"' . ?s <http://purl.org/dh/0.1/password> '"+password+"' }", ResultSetFormatter::out);
		 ret = conn.queryAsk("ASK { ?s <http://xmlns.com/foaf/0.1/mbox> '"+email+"' . ?s <http://purl.org/dailyhire/0.1/password> '"+password+"' }");
		}

		/*
		 * try (RDFConnectionFuseki conn2 = (RDFConnectionFuseki)builder.build() ){
		 * conn2.
		 * querySelect("SELECT DISTINCT ?name ?email ?gender ?dateOfBirth  ?language ?latitude ?longitude ?telephone ?faxNumber ?password ?buildingName ?landmark ?streetAddress ?locality ?state ?countryName ?postalCode"
		 * + "WHERE {?s ?p ?o ." +
		 * "<"+identifier+"> <http://xmlns.com/foaf/0.1/name> ?name . " +
		 * "<"+identifier+">  <http://xmlns.com/foaf/0.1/mbox> ?email . " +
		 * "<"+identifier+"> <http://xmlns.com/foaf/0.1/gender> ?gender . " +
		 * "<"+identifier+">  <http://www.w3.org/2006/vcard/ns#bday> '?dateOfBirth ." +
		 * "<"+identifier+">  <http://purl.org/dc/terms/language> ?language ." +
		 * "<"+identifier+">  <http://purl.org/dailyhire/0.1/latitude> ?latitude ." +
		 * "<"+identifier+">  <http://purl.org/dailyhire/0.1/longitude> ?longitude  ." +
		 * "<"+identifier+">  <http://schema.org/telephone> ?telephone ." +
		 * "<"+identifier+">  <http://schema.org/faxNumber> ?faxNumber ." +
		 * "<"+identifier+">  <http://purl.org/dailyhire/0.1/password> ?password ." +
		 * "<"+
		 * identifier+">  <http://purl.org/dailyhire/0.1/buildingName> ?buildingName ."
		 * + "<"+identifier+">  <http://purl.org/dailyhire/0.1/landmark> ?landmark ." +
		 * "<"+identifier+">  <http://rdfs.co/juso/full_address> ?streetAddress . " +
		 * "<"+identifier+">  <http://www.w3.org/2006/vcard/ns#locality> ?locality ." +
		 * "<"+identifier+">  <http://purl.org/essglobal/vocab/state> ?state ." +
		 * "<"+identifier+">  <http://rdfs.co/juso/country-name> ?countryName . " + "<"+
		 * identifier+">  <http://www.w3.org/2006/vcard/ns#postal-code> ?postalCode . }"
		 * , (qs)->{ Resource objectName = qs.getResource("name");
		 * System.out.println("Employee name:" +objectName); }); }
		 */
		
		

		try (RDFConnectionFuseki conn2 = (RDFConnectionFuseki)builder.build() ){
			conn2.querySelect("SELECT DISTINCT ?name"
					+ "WHERE {?s ?p ?o ."
					+ "<"+identifier+"> <http://xmlns.com/foaf/0.1/name> ?name . }", (qs)->{
					Literal objectName = qs.getLiteral("name");
					
					System.out.println("Employee name:" +objectName);
					});
		}
		
		
		System.out.println("ret is = " + ret);
		return ret;
	}


}
