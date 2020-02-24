package org.dailyhiring.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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


			// when passwords match
			if (password.equals(request.getParameter("password"))) {
				ret = new Worker(latitude, longitude, name, gender, language, 
						dateOfBirth, email, faxNumber,telephoneNumber, password, 
						buildingName, landmark, streetAddress, countryName, postalCode);
				request.getSession().setAttribute("worker", ret);
				
			}
			
		} qe3.close();

		return ret;
	}

}
