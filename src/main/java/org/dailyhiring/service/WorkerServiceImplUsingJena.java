package org.dailyhiring.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.dailyhiring.dao.JobOfferRepository;
import org.dailyhiring.dao.WorkerRepository;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkerServiceImplUsingJena implements WorkerService {
	private WorkerRepository workerRepository;
	private static RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
			.destination("http://localhost:3030/dh");

	
	@Autowired
	private JobOfferRepository jobOfferRepository;

	public WorkerServiceImplUsingJena() {
		super();
	}

	@Autowired
	public WorkerServiceImplUsingJena(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}
	
	
	@Override
	public Worker save(Worker worker) {
		String name = worker.getName();
		String email = worker.getEmail();
		String password = worker.getPassword();

		Integer defaultPayVisit = worker.getDefaultPayVisit();
		String skillType = worker.getSkillType();
		Double experience = worker.getExperience();
		Integer payAmount = worker.getPayAmount();
		String typeOfPayAmount = worker.getTypeOfPayAmount();
		String certificate = worker.getCertificate();
		String placePreference = worker.getPlacePreference();
		
		String gender = worker.getGender();
		String language = worker.getLanguage();
		String dateOfBirth = worker.getDateOfBirth();
		Double latitude = worker.getLatitude();
		Double longitude = worker.getLongitude();
		
		String telephone = worker.getTelephoneNumber();
		String fax = worker.getFaxNumber();
		String buildingName = worker.getAddress().getBuildingName();
		String identifier = "http://purl.org/dh/0.1/"+worker.getEmail();
		String landmark = worker.getAddress().getLandmark();
		String streetAddress = worker.getAddress().getStreetAddress();
		String locality = worker.getAddress().getLocality();
		String state = worker.getAddress().getState();
		String countryName = worker.getAddress().getCountryName();
		String postalCode = worker.getAddress().getPostalCode();
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
					+ "<"+identifier+">  dh:password '"+password+"' ."
					+ "<"+identifier+">  dh:defaultPayVisit '"+defaultPayVisit+"'^^xsd:int ."					
					+ "<"+identifier+">  dh:skillType '"+skillType+"' ."
					+ " <"+identifier+">  dh:experience '"+experience+"'^^xsd:decimal ."
					+ "<"+identifier+">  dh:payAmount '"+payAmount+"'^^xsd:int ."
					+ "<"+identifier+">  dh:typeOfPayAmount '"+typeOfPayAmount+"' ."
					+ "<"+identifier+">  dh:certificate '"+certificate+"' ."
					+ "<"+identifier+">  dh:placePreference '"+placePreference+"' ."
					+ "<"+identifier+"> foaf:gender '"+gender+"' ."
					+ " <"+identifier+">  vcard:bday '"+dateOfBirth+"'^^xsd:date ."
					+ " <"+identifier+">  dc:language '"+language+"' ."
					+ " <"+identifier+">  dh:latitude '"+latitude+"'^^xsd:decimal ."
					+ "<"+identifier+">  dh:longitude '"+longitude+"'^^xsd:decimal  ."
					+ "<"+identifier+">  schema:telephone '"+telephone+"' ."
					+ "<"+identifier+">  schema:faxNumber '"+fax+"' ."
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
				
			//workerRepository.save(worker);
		return new Worker();

	
	
	}

	@Override
	public Worker findById(int theId) {
		Worker ret = null;
		Optional<Worker> optionalWorker =  workerRepository.
				findById(theId);
		if (optionalWorker.isPresent()) {
			ret = optionalWorker.get();
		}
		return ret;

	}

	@Override
	public void applyInJob(Integer workerId, Integer theJobId) {
		Worker worker = null;
		Optional<Worker> optionalWorker =  workerRepository.
				findById(workerId);
		if (optionalWorker.isPresent()) {
			worker = optionalWorker.get();
		}
		
		JobOffer jobOffer = null;
		Optional<JobOffer> optionalJobOffer =  jobOfferRepository.
				findById(theJobId);
		if (optionalJobOffer.isPresent()) {
			jobOffer = optionalJobOffer.get();
		}		
		
		
		jobOffer.addApplicantWorker(worker);
		jobOfferRepository.save(jobOffer);
		
	}

	public Worker findById(String emailId, HttpServletRequest request) {
		Worker ret = null;
		String identifier = "<http://purl.org/dh/0.1/"+emailId+">";
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query","PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>\r\n" + 
				"PREFIX dc:<http://purl.org/dc/terms/>\r\n" + 
				"PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n" + 
				"PREFIX schema:<http://schema.org/>\r\n" + 
				"PREFIX juso:<http://rdfs.co/juso/>\r\n" + 
				"PREFIX essglobal:<http://purl.org/essglobal/vocab/>\r\n" + 
				//"SELECT ?name ?email ?gender ?dateOfBirth ?language ?latitude ?longitude ?telephone ?fax ?password ?buildingName ?landmark ?streetAddress ?countryName ?postalCode\r\n" + 
				
				
				"SELECT * \r\n" +
				"WHERE {\r\n" 
				+identifier+" foaf:name ?name .\r\n" + 
				identifier+"  foaf:mbox ?email .\r\n" +
				identifier+"  dh:defaultPayVisit ?defaultPayVisit .\r\n" +
				identifier+"  dh:skillType ?skillType .\r\n" +
				identifier+"  dh:experience ?experience .\r\n" +
				identifier+"  dh:payAmount ?payAmount .\r\n" +
				identifier+"  dh:typeOfPayAmount ?typeOfPayAmount .\r\n" +
				identifier+"  dh:certificate ?certificate .\r\n" +
				identifier+"  dh:placePreference ?placePreference .\r\n" +
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
				identifier+"  vcard:locality ?locality .\r\n"+
				identifier+"  essglobal:state ?state .\r\n"+
				identifier+"  juso:country-name ?countryName .\r\n" + 
				identifier+"  vcard:postal-code ?postalCode . \r\n" + 
				"}");
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext() ) {
			System.out.println(">>>>>>>>>>>>>>> QE3 Hurray:");
			QuerySolution sln = results3.nextSolution();
			String name = sln.getLiteral("name").toString();
			String email = sln.getLiteral("email").toString();
			String password = sln.getLiteral("password").toString();

			// Getting defaultPayVisit 
			String defaultPayVisitAsString = sln.getLiteral("defaultPayVisit").
					toString().replace("^^http://www.w3.org/2001/XMLSchema#int", "");
			Integer defaultPayVisit = null;
			if (!defaultPayVisitAsString.equals("null") ) {
				defaultPayVisit = Integer.parseInt(defaultPayVisitAsString);
			}
			
			String skillType = sln.getLiteral("skillType").toString();
			// Getting experience
			String experienceAsString = sln.getLiteral("experience").
					toString().replace("^^http://www.w3.org/2001/XMLSchema#decimal", "");
			Double experience = null;
			if (!experienceAsString.equals("null") ) {
				experience = Double.parseDouble(experienceAsString);
			}

			
			// Getting payAmount 
			String payAmountAsString = sln.getLiteral("payAmount").
					toString().replace("^^http://www.w3.org/2001/XMLSchema#int", "");
			Integer payAmount = null;
			if (!payAmountAsString.equals("null") ) {
				payAmount = Integer.parseInt(payAmountAsString);
			}
			
			
			String typeOfPayAmount = sln.getLiteral("typeOfPayAmount").toString();
			String certificate = sln.getLiteral("certificate").toString();
			String placePreference = sln.getLiteral("placePreference").toString();
			
			String gender = sln.getLiteral("gender").toString();
			String dateOfBirth = sln.getLiteral("dateOfBirth").toString().replace("^^http://www.w3.org/2001/XMLSchema#date", "");
			String language = sln.getLiteral("language").toString();
			Double latitude = sln.getLiteral("latitude").getDouble();
			Double longitude = sln.getLiteral("longitude").getDouble();
			String telephoneNumber = sln.getLiteral("telephone").toString();
			String faxNumber = sln.getLiteral("fax").toString();
			String buildingName = sln.getLiteral("buildingName").toString();
			String landmark = sln.getLiteral("landmark").toString();
			String streetAddress = sln.getLiteral("streetAddress").toString();
			// todo - add locality and state fields
			String locality = "";//sln.getLiteral("locality").toString();// gives null pointer exception
			String state = "";//sln.getLiteral("state").toString();// gives null pointer exception

			String countryName = sln.getLiteral("countryName").toString();
			String postalCode = sln.getLiteral("postalCode").toString();


			// when passwords match
			if (password.equals(request.getParameter("password"))) {
				/*
				ret = new Worker(latitude, longitude, name, gender, language, 
						dateOfBirth, email, faxNumber,telephoneNumber, password, 
						buildingName, landmark, streetAddress, countryName, postalCode);
				*/
				/*
				ret = new Worker(defaultPayVisit, skillType, experience, payAmount, 
						typeOfPayAmount, certificate, placePreference, latitude, 
						longitude, name, gender, language, dateOfBirth, email, 
						faxNumber, telephoneNumber, password, buildingName, 
						landmark, streetAddress, countryName, postalCode);
				*/
				ret = new Worker(defaultPayVisit, skillType, experience, payAmount, 
						typeOfPayAmount, certificate, placePreference, latitude, 
						longitude, name, gender, language, dateOfBirth, email, 
						faxNumber, telephoneNumber, password, buildingName, 
						landmark, streetAddress, locality, state, countryName, postalCode);
				
				
				
				
				request.getSession().setAttribute("worker", ret);
				
				// so that worker can access as employer as well 
				Employer ret2 = new Employer(latitude, longitude, name, gender, language, 
						dateOfBirth, email, faxNumber,telephoneNumber, password, 
						buildingName, landmark, streetAddress, locality,state, 
						countryName, postalCode);
				request.getSession().setAttribute("employer", ret2);
				
			}
			
		} qe3.close();

		return ret;
	}

	public void applyInJob(String workerEmail, Integer theJobIdInFuseki,
			org.apache.catalina.servlet4preview.http.HttpServletRequest request) {
		Integer jobOpenings = 0;
		Integer jobsAlreadyFilled = 0;
		// Get totalJobOpenings and jobOpeningsAlreadyFilled
		String identifier = "<http://purl.org/dh/0.1/"+theJobIdInFuseki+">";
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query",
				
				"SELECT * \r\n" +
				"WHERE {\r\n"+ 
				"<http://server/unset-base/"+theJobIdInFuseki+">  <http://purl.org/dailyhire/0.1/jobOpenings> ?jobOpenings .\r\n" +
				"<http://server/unset-base/"+theJobIdInFuseki+"> <http://purl.org/dailyhire/0.1/jobOpeningsAlreadyFilled> ?jobsAlreadyFilled .\r\n" + 
				"}");
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext() ) {
			QuerySolution sln = results3.nextSolution();
			jobOpenings= sln.getLiteral("jobOpenings").getInt();
			jobsAlreadyFilled = sln.getLiteral("jobsAlreadyFilled").getInt();  

			System.out.println("RZ>>>>>>>>>>>>jobOpenings = " + jobOpenings );
			System.out.println("RZ>>>>>>>>>>>>jobsAlreadyFilled = " + jobsAlreadyFilled );
			}qe3.close();

		// Apply 	
		if (jobOpenings > jobsAlreadyFilled) {
			try (RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ){
				conn.update("PREFIX dh:<http://purl.org/dailyhire/0.1/>"
						+ "INSERT DATA { <"+workerEmail+"> dh:hasAppliedInTheJobWithId '"+theJobIdInFuseki.toString()+"' ."
						+ "}"  );
				
				// update the field <http://purl.org/dailyhire/0.1/jobOpeningsAlreadyFilled> for this job
				
				conn.update("delete where{ <http://server/unset-base/"+theJobIdInFuseki+ "> <http://purl.org/dailyhire/0.1/jobOpeningsAlreadyFilled> ?o }");

				conn.update("PREFIX dh:<http://purl.org/dailyhire/0.1/>"
						+ "INSERT DATA { <http://server/unset-base/"+theJobIdInFuseki+"> <http://purl.org/dailyhire/0.1/jobOpeningsAlreadyFilled> '"+(++jobsAlreadyFilled).toString()+"' }");

				
				}
			
		}
			

	}
	

	public Worker editProfile(@Valid Worker newWorker, HttpServletRequest request) {
		Worker worker = (Worker) request.getSession().getAttribute("worker");
		String name = worker.getName();
		String email = worker.getEmail();
		String gender = worker.getGender();
		String dateOfBirth = worker.getDateOfBirth();
		String language = worker.getLanguage();
		Double latitude = worker.getLatitude();
		Double longitude = worker.getLongitude();
		String password = worker.getPassword();
		String telephone = worker.getTelephoneNumber();
		String fax = worker.getFaxNumber();
		String buildingName = worker.getAddress().getBuildingName();
		String identifier = "http://purl.org/dh/0.1/"+worker.getEmail();
		String landmark = worker.getAddress().getLandmark();
		String streetAddress = worker.getAddress().getStreetAddress();
		String locality = worker.getAddress().getLocality();
		String state = worker.getAddress().getState();
		String countryName = worker.getAddress().getCountryName();
		String postalCode = worker.getAddress().getPostalCode();
		System.out.println(latitude);
		try (RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ){
			/*
			conn.update("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
					+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
					+ "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>"
					+ "PREFIX dc:<http://purl.org/dc/terms/>"
					+ "PREFIX dh:<http://purl.org/dailyhire/0.1/>"
					+ "PREFIX schema:<http://schema.org/>"
					+ "PREFIX essglobal:<http://purl.org/essglobal/vocab/>"
					+ "PREFIX juso:<http://rdfs.co/juso/>" 
					
					+ "DELETE\r\n" + 
					"WHERE {\r\n"
					+ " <"+identifier+"> foaf:name '"+name+"' ."
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
					 */
			
			conn.update("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\r\n"
					+ "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>\r\n"
					+ "PREFIX dc:<http://purl.org/dc/terms/>\r\n"
					+ "PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n"
					+ "PREFIX schema:<http://schema.org/>\r\n"
					+ "PREFIX essglobal:<http://purl.org/essglobal/vocab/>\r\n"
					+ "PREFIX juso:<http://rdfs.co/juso/>\r\n" 
					+ "DELETE\r\n" + 
					"WHERE {\r\n"
					+" <"+identifier+"> foaf:name ?name ."
					+ " <"+identifier+">  foaf:mbox ?email ."
					+ "<"+identifier+">  dh:password ?password ."
					+ "<"+identifier+">  dh:defaultPayVisit ?defaultPayVisit ."
					+ "<"+identifier+">  dh:skillType ?skillType ."
					+ "<"+identifier+">  dh:experience ?experience ."
					+ "<"+identifier+">  dh:payAmount ?payAmount ."
					+ "<"+identifier+">  dh:typeOfPayAmount ?typeOfPayAmount ."
					+ "<"+identifier+">  dh:certificate ?certificate ."
					+ "<"+identifier+">  dh:placePreference ?placePreference ."
					+ "<"+identifier+"> foaf:gender ?gender ."
					+ " <"+identifier+">  vcard:bday ?dob ."
					+ " <"+identifier+">  dc:language ?language ."
					+ " <"+identifier+">  dh:latitude ?latitude ."
					+ "<"+identifier+">  dh:longitude ?longitude ."
					+ "<"+identifier+">  schema:telephone ?telephone ."
					+ "<"+identifier+">  schema:faxNumber ?fax ."
					+ "<"+identifier+">  dh:buildingName ?buildingName ."
					+ "<"+identifier+">  dh:landmark ?landmark ."
					+ "<"+identifier+">  juso:full_address ?streetAddress ."
					+ "<"+identifier+">  vcard:locality ?locality ."
					+ "<"+identifier+">  essglobal:state ?state ."
					+ "<"+identifier+">  juso:country-name ?countryName ."
					+ "<"+identifier+">  vcard:postal-code ?postalCode ."
					+ "}"
					 );

			//conn.update("DELETE WHERE {?s ?p ?o}");
								
			}

		
		this.save(newWorker);
		request.getSession().setAttribute("worker",newWorker);
		return newWorker;

	}

}
