package org.dailyhiring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.dailyhiring.dao.EmployerRepository;
import org.dailyhiring.dao.JobOfferRepository;
import org.dailyhiring.dao.WorkerRepository;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class JobOfferServiceImplUsingJena implements JobOfferService {

	private JobOfferRepository jobOfferRepository;
	@Autowired
	private WorkerRepository workerRepository;
	@Autowired
	private EmployerRepository employerRepository;

	private static RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
			.destination("http://localhost:3030/dh");

	@Autowired
	public JobOfferServiceImplUsingJena(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	public JobOfferServiceImplUsingJena() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JobOffer save(JobOffer jobOffer) {

		String employerEmail = jobOffer.getEmployerEmail();
		String appointmentDescription = jobOffer.getAppointmentDescription();
		Integer jobOpenings = jobOffer.getJobOpenings();
		Integer jobOpeningsAlreadyFilled = jobOffer.getJobOpeningsAlreadyFilled();
		String jobStarts = jobOffer.getJobStarts();
		String jobEnds = jobOffer.getJobEnds();
		String paymentType = jobOffer.getPaymentType();
		String paymentMode = jobOffer.getPaymentMode();
		Double ratingRequired = jobOffer.getRatingRequired();

		String identifier = jobOffer.getJobIdInFuseki().toString();

		try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
			conn.update("PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>" + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
					+ "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>" + "PREFIX dc:<http://purl.org/dc/terms/>"
					+ "PREFIX dh:<http://purl.org/dailyhire/0.1/>" + "PREFIX schema:<http://schema.org/>"
					+ "PREFIX essglobal:<http://purl.org/essglobal/vocab/>" + "PREFIX juso:<http://rdfs.co/juso/>"

					+ "INSERT DATA { <" + identifier + "> dh:jobOfferId '" + identifier + "' ." + " <" + identifier
					+ ">  foaf:mbox '" + employerEmail + "' ." + "<" + identifier + "> dh:appointmentDescription '"
					+ appointmentDescription + "' ." + " <" + identifier + ">  dh:jobOpenings '"
					+ jobOpenings.toString() + "' ." + " <" + identifier + ">  dh:jobOpeningsAlreadyFilled '"
					+ jobOpeningsAlreadyFilled.toString() + "' ." + " <" + identifier + ">  dh:jobStarts '" + jobStarts
					+ "'^^xsd:date ." + " <" + identifier + ">  dh:jobEnds '" + jobEnds + "'^^xsd:date ." + " <"
					+ identifier + ">  dh:ratingRequired '" + ratingRequired + "'^^xsd:decimal ." + "<" + identifier
					+ ">  dh:paymentType '" + paymentType + "' ." + "<" + identifier + ">  dh:paymentMode '"
					+ paymentMode + "' ." + "}");
			// conn.queryResultSet("SELECT ?s ?p WHERE { ?s ?p <o:Rameez> }",
			// ResultSetFormatter::out);

		}

		return new JobOffer();
	}

	@Override
	public List<JobOffer> findAll() {
		return jobOfferRepository.findAllByOrderByJobIdAsc();
	}

	/*
	 * @Override public List<JobOffer> findAllJobsMatchingFieldOfWork(int
	 * theWorkerId) { List<JobOffer> jobOffers =
	 * jobOfferRepository.findAllByOrderByJobIdAsc(); Optional<Worker>
	 * optionalWorker = workerRepository.findById(theWorkerId); Worker worker =
	 * optionalWorker.get();
	 * 
	 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
	 * { JobOffer nextJobOffer = iterator.next(); if
	 * (!(nextJobOffer.getFieldOfWork().getName().equals(worker.getSkill().
	 * getFieldOfWork().getName()))) { iterator.remove(); } } return jobOffers; }
	 */
	/*
	 * @Override public List<JobOffer> findAllJobsMatchingCertificate(int
	 * theWorkerId) { List<JobOffer> jobOffers =
	 * jobOfferRepository.findAllByOrderByJobIdAsc(); Optional<Worker>
	 * optionalWorker = workerRepository.findById(theWorkerId); Worker worker =
	 * optionalWorker.get();
	 * 
	 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
	 * { JobOffer nextJobOffer = iterator.next(); if
	 * (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().
	 * getName()))) { iterator.remove(); } } return jobOffers; }
	 */

	@Override
	public JobOffer save(@Valid JobOffer jobOffer, int employerId) {
		/*
		 * Optional<Employer> optionalEmployer =
		 * employerRepository.findById(employerId); Employer employer = null; if
		 * (optionalEmployer.isPresent()) { employer = optionalEmployer.get();
		 * employer.add(jobOffer); employerRepository.save(employer); }
		 */
		return jobOffer;
	}

	@Override
	public List<JobOffer> findAllMatchingJobs(int theWorkerId) {
		List<JobOffer> jobOffers = jobOfferRepository.findAllByOrderByJobIdAsc();
		Optional<Worker> optionalWorker = workerRepository.findById(theWorkerId);
		Worker worker = optionalWorker.get();
		System.out.println("\t\t>>>>>>>>>" + this.getClass());
		System.out.println("\t\t>>>>>>>>> Latitude of worker retrieved from db is : " + worker.getLatitude());

		// Matching field of work

		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (!(nextJobOffer.getFieldOfWork().getName().equals(worker.getSkill().
		 * getFieldOfWork().getName()))) { iterator.remove(); }
		 * 
		 * } System.out.println("After matching field of work -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Certificate
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getCertificate().getName().equals("None")) { continue; } if
		 * (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().
		 * getName()))) { iterator.remove(); } }
		 * System.out.println("After matching certificate -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Experience Requirement
		/*
		 * if (worker.getexperienceYears() != null) { for (Iterator<JobOffer> iterator =
		 * jobOffers.iterator(); iterator.hasNext();) { JobOffer nextJobOffer =
		 * iterator.next(); if (nextJobOffer.getexperienceYears() >
		 * worker.getexperienceYears()) { iterator.remove(); } } }
		 * System.out.println("After matching experience -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Degree Requirement
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getDegree().getName().equals("None")) { continue; } if
		 * (!(nextJobOffer.getDegree().getName().equals(worker.getDegree().getName())))
		 * { iterator.remove(); } }
		 */ System.out.println("After matching degree requirement -> jobOffers.isEmpty()" + jobOffers.isEmpty());

		// Matching Diploma Requirement
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getDiploma().getName().equals("None")) { continue; } if
		 * (!(nextJobOffer.getDiploma().getName().equals(worker.getDiploma().getName()))
		 * ) { iterator.remove(); } } System.out.
		 * println("After matching diploma requirement -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Training Requirement
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getTraining().getName().equals("None")) { continue; }
		 * 
		 * if
		 * (!(nextJobOffer.getTraining().getName().equals(worker.getTraining().getName()
		 * ))) { iterator.remove(); } } System.out.
		 * println("After matching training requirement -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Location to be less than 100 km
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			/*
			 * if (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().
			 * getName()))) { iterator.remove(); }
			 */
			Double employerLatitude = nextJobOffer.getEmployer().getLatitude();
			Double employerLongitude = nextJobOffer.getEmployer().getLongitude();
			Double workerLatitude = worker.getLatitude();
			Double workerLongitude = worker.getLongitude();

			Double distanceBetweenEmployerAndWorker = calculateDistanceUsingLocation(employerLatitude,
					employerLongitude, workerLatitude, workerLongitude, "K");
			// System.out.println("Distance between Employer and Worker:
			// "+distanceBetweenEmployerAndWorker + "km");
			if (distanceBetweenEmployerAndWorker > 100) {
				iterator.remove();
			}
		}
		System.out.println("After matching location -> jobOffers.isEmpty()" + jobOffers.isEmpty());

		// Remove Jobs in which worker has already applied
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next();
		 * 
		 * List<JobOffer> jobsAppliedIn = worker.getJobsAppliedIn(); for
		 * (Iterator<JobOffer> jobsAppliedInIterator = jobsAppliedIn.iterator();
		 * jobsAppliedInIterator .hasNext();) { JobOffer nextJobAppliedIn =
		 * jobsAppliedInIterator.next();
		 * 
		 * if (nextJobOffer.getJobId() == nextJobAppliedIn.getJobId()) {
		 * iterator.remove(); break; } }
		 * 
		 * } System.out.println(
		 * "After removing where worker has already applied -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Remove Jobs where all the jobOpenings have been filled (ie
		// that many workers have already applied)
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (nextJobOffer.getJobOpenings() == nextJobOffer.getJobOpeningsAlreadyFilled()) {
				iterator.remove();
				break;
			}

		}
		System.out.println(
				"After removing where Job openings have been filled -> jobOffers.isEmpty()" + jobOffers.isEmpty());

		// Remove Jobs where Job Valid period (date) has already passed

		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next();
		 * 
		 * if (nextJobOffer.getValidThrough() != null &&
		 * !nextJobOffer.getValidThrough().isEmpty()) {
		 * 
		 * try { Date todaysDate = new Date(); todaysDate.setHours(0); Date
		 * validThroughDate = new
		 * SimpleDateFormat("yyyy-MM-dd").parse(nextJobOffer.getValidThrough());
		 * validThroughDate.setHours(1); if (validThroughDate.before(todaysDate)) {
		 * System.out.print("validThroughDate -> " + validThroughDate);
		 * System.out.println(" | todaysDate -> " + todaysDate); iterator.remove(); } }
		 * catch (ParseException e) { e.printStackTrace(); } } } System.out.
		 * println("After removing where Job validity has passed -> jobOffers.isEmpty()"
		 * + jobOffers.isEmpty());
		 */

		// Remove Jobs recommendation requirement is not satisfied.
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getRecommendation() != null) { if
		 * (nextJobOffer.getRecommendation() > worker.getRecommendation()) {
		 * iterator.remove(); } } }
		 */ System.out.println("After removing where recommendation requirement is not satisfied"
				+ " -> jobOffers.isEmpty()" + jobOffers.isEmpty());

		// Remove Jobs where competency level requirement is not satisfied
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getCompetencyLevel() != null) { if
		 * (nextJobOffer.getCompetencyLevel() > worker.getCompetencyLevel()) {
		 * iterator.remove(); }
		 * 
		 * } } System.out.
		 * println("After removing where Competency requirement is not satisfied" +
		 * " -> jobOffers.isEmpty()" + jobOffers.isEmpty());
		 */
		return jobOffers;
	}

	/*
	 * Each degree of latitude is approximately 69 miles (111 kilometers) apart. The
	 * range varies (due to the earth's slightly ellipsoid shape) from 68.703 miles
	 * (110.567 km) at the equator to 69.407 (111.699 km) at the poles. This is
	 * convenient because each minute (1/60th of a degree) is approximately one
	 * [nautical] mile.
	 * 
	 */

	/*
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	 */
	/* :: : */
	/* :: This routine calculates the distance between two points (given the : */
	/* :: latitude/longitude of those points). It is being used to calculate : */
	/* :: the distance between two locations using GeoDataSource (TM) products : */
	/* :: : */
	/* :: Definitions: : */
	/* :: South latitudes are negative, east longitudes are positive : */
	/* :: : */
	/* :: Passed to function: : */
	/* :: lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees) : */
	/* :: lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees) : */
	/* :: unit = the unit you desire for results : */
	/* :: where: 'M' is statute miles (default) : */
	/* :: 'K' is kilometers : */
	/* :: 'N' is nautical miles : */
	/* :: : */

	private Double calculateDistanceUsingLocation(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0.0;
		} else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			// km
			if (unit == "K") {
				dist = dist * 1.609344;
			} else if (unit == "N") {
				dist = dist * 0.8684;
			}
			return (dist);
		}

	}

	@Override
	public List<JobOffer> findAllJobsPostedBy(Integer employerId) {
		List<JobOffer> jobOffers = jobOfferRepository.findAllByOrderByJobIdAsc();

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (nextJobOffer.getEmployer().getId() != employerId) {
				iterator.remove();
			}
		}
		return jobOffers;
	}

	@Override
	public List<JobOffer> findAllJobsAppliedBy(Integer workerId) {
		System.out.println("findAllJobsAppliedBy() inside " + this.getClass() + " starts!");
		List<JobOffer> jobOffers = jobOfferRepository.findAllByOrderByJobIdAsc();
		Optional<Worker> optionalWorker = workerRepository.findById(workerId);
		Worker worker = optionalWorker.get();

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (!(nextJobOffer.getApplicantWorkers().contains(worker))) {
				System.out.println("Job with id = " + nextJobOffer.getJobId() + " is getting removed!");
				iterator.remove();
			}
		}
		System.out.println("findAllJobsAppliedBy() inside " + this.getClass() + " ends!");

		return jobOffers;
	}

	@Override
	public List<JobOffer> findAllMatchingJobs(String theWorkerEmail, HttpServletRequest request) {
		Worker worker = (Worker) request.getSession().getAttribute("worker");
		System.out.println(
				">>>>>>>>>>>>>>> INSIDE public List<JobOffer> findAllMatchingJobs(String theWorkerEmail) - STARTS ");
		List<JobOffer> jobOffers = new ArrayList<JobOffer>();
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query",
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + "PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n"
						+ "SELECT * \r\n" + "WHERE {\r\n" + "?jobId dh:jobOfferId ?jobIdInFuseki .\r\n"
						+ "?jobId foaf:mbox ?employerEmail .\r\n"
						+ "?jobId dh:appointmentDescription ?appointmentDescription .\r\n"
						+ "?jobId dh:jobOpenings ?jobOpenings .\r\n"
						+ "?jobId dh:jobOpeningsAlreadyFilled ?jobOpeningsAlreadyFilled .\r\n"
						+ "?jobId dh:jobStarts ?jobStarts .\r\n" + "?jobId dh:jobEnds ?jobEnds . \r\n"
						+ "?jobId dh:ratingRequired ?ratingRequired .\r\n" + "?jobId dh:paymentType ?paymentType .\r\n"
						+ "?jobId dh:paymentMode ?paymentMode .\r\n" + "}");
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext()) {
			System.out.println(">>>>>>>>>>>>>>> QE3 Hurray : Going to add jobs to the Arraylist");
			QuerySolution sln = results3.nextSolution();
			System.out.println(">>>>>>>>>>>>>>> 1");
			Integer jobIdInFuseki = Integer.parseInt(sln.getLiteral("jobIdInFuseki").toString());
			System.out.println(">>>>>>>>>>>>>>> 2");
			String employerEmail = sln.getLiteral("employerEmail").toString();
			System.out.println(">>>>>>>>>>>>>>> 3");
			String appointmentDescription = sln.getLiteral("appointmentDescription").toString();
			Integer jobOpenings = Integer.parseInt(sln.getLiteral("jobOpenings").toString());
			Integer jobOpeningsAlreadyFilled = Integer.parseInt(sln.getLiteral("jobOpeningsAlreadyFilled").toString());
			// String dateOfBirth =
			// sln.getLiteral("dateOfBirth").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
			// "");
			String jobStarts = sln.getLiteral("jobStarts").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");
			String jobEnds = sln.getLiteral("jobEnds").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");

			// Double latitude = sln.getLiteral("latitude").getDouble();
			// Double longitude = sln.getLiteral("longitude").getDouble();
			Double ratingRequired = sln.getLiteral("ratingRequired").getDouble();
			String paymentType = sln.getLiteral("paymentType").toString();
			String paymentMode = sln.getLiteral("paymentMode").toString();

			JobOffer jobOffer = new JobOffer(jobIdInFuseki, appointmentDescription, jobOpenings,
					jobOpeningsAlreadyFilled, jobStarts, jobEnds, paymentMode, paymentType, employerEmail,
					ratingRequired);

			jobOffers.add(jobOffer);

		}
		qe3.close();
		
		// Now, jobOffers contain all the jobs inserted in fuseki.
		// Matching appointment Description of Job with Skill Type of worker

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (!(nextJobOffer.
					getAppointmentDescription().
					equals(worker.
							getSkillType()))) {
				iterator.remove();
			}

		}
		// System.out.println("After matching field of work -> jobOffers.isEmpty()" +
		// jobOffers.isEmpty());

		// Matching Certificate
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getCertificate().getName().equals("None")) { continue; } if
		 * (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().
		 * getName()))) { iterator.remove(); } }
		 * System.out.println("After matching certificate -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */

		// Matching Experience Requirement
		/*
		 * if (worker.getexperienceYears() != null) { for (Iterator<JobOffer> iterator =
		 * jobOffers.iterator(); iterator.hasNext();) { JobOffer nextJobOffer =
		 * iterator.next(); if (nextJobOffer.getexperienceYears() >
		 * worker.getexperienceYears()) { iterator.remove(); } } }
		 * System.out.println("After matching experience -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Matching Location to be less than 100 km
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next();
		 * 
		 * if (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().
		 * getName()))) { iterator.remove(); }
		 * 
		 * Double employerLatitude = nextJobOffer.getEmployer().getLatitude(); Double
		 * employerLongitude = nextJobOffer.getEmployer().getLongitude(); Double
		 * workerLatitude = worker.getLatitude(); Double workerLongitude =
		 * worker.getLongitude();
		 * 
		 * Double distanceBetweenEmployerAndWorker =
		 * calculateDistanceUsingLocation(employerLatitude, employerLongitude,
		 * workerLatitude, workerLongitude, "K"); // System.out.println("Distance
		 * between Employer and Worker: // "+distanceBetweenEmployerAndWorker + "km");
		 * if (distanceBetweenEmployerAndWorker > 100) { iterator.remove(); } }
		 * System.out.println("After matching location -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		
		// Remove Jobs in which worker has already applied
		
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();

			List<Integer> jobsAppliedIn = this.getJobIdsOfJobsAppliedIn(theWorkerEmail);
			//List<JobOffer> jobsAppliedIn = worker.getJobsAppliedIn();
			for (Iterator<Integer> jobsAppliedInIterator = jobsAppliedIn.iterator(); jobsAppliedInIterator
					.hasNext();) {
				Integer nextJobAppliedIn = jobsAppliedInIterator.next();
				System.out.println("nextJobOffer.getJobIdInFuseki() is " + nextJobOffer.getJobIdInFuseki() +
						"and nextJobAppliedIn is "+ nextJobAppliedIn);
				if (nextJobOffer.getJobIdInFuseki().equals(nextJobAppliedIn)) {
					System.out.println(">>>>>>>>>>>>>A job being removed from jobOffers");
					iterator.remove();
				}
			}

		}
		System.out.println(
				"After removing where worker has already applied -> jobOffers.isEmpty()" + jobOffers.isEmpty());
		 
		// Remove Jobs where all the jobOpenings have been filled (ie
		// that many workers have already applied)
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if (nextJobOffer.getJobOpenings()
		 * == nextJobOffer.getJobOpeningsAlreadyFilled()) { iterator.remove(); break; }
		 * 
		 * } System.out.println(
		 * "After removing where Job openings have been filled -> jobOffers.isEmpty()" +
		 * jobOffers.isEmpty());
		 */
		// Remove Jobs where Job Valid period (date) has already passed

		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next();
		 * 
		 * if (nextJobOffer.getValidThrough() != null &&
		 * !nextJobOffer.getValidThrough().isEmpty()) {
		 * 
		 * try { Date todaysDate = new Date(); todaysDate.setHours(0); Date
		 * validThroughDate = new
		 * SimpleDateFormat("yyyy-MM-dd").parse(nextJobOffer.getValidThrough());
		 * validThroughDate.setHours(1); if (validThroughDate.before(todaysDate)) {
		 * System.out.print("validThroughDate -> " + validThroughDate);
		 * System.out.println(" | todaysDate -> " + todaysDate); iterator.remove(); } }
		 * catch (ParseException e) { e.printStackTrace(); } } } System.out.
		 * println("After removing where Job validity has passed -> jobOffers.isEmpty()"
		 * + jobOffers.isEmpty());
		 */

		return jobOffers;
	}

	private List<Integer> getJobIdsOfJobsAppliedIn(String theWorkerEmail) {
		List<Integer> jobIdsOfJobsAppliedIn = new ArrayList<Integer>();

		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query",
							"PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n"
						+ "SELECT * \r\n" + 
						"WHERE {\r\n" + "?email dh:hasAppliedInTheJobWithId ?jobIdInFuseki .\r\n" + 
						"}");
		
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext()) {
			QuerySolution sln = results3.nextSolution();
			String email = sln.getResource("email").toString().
					replace("http://server/unset-base/", "");
			System.out.println("email got using getResource() is ->>>>>>>>>> " + email);
			
			if (email.equals(theWorkerEmail)) {
				Integer jobIdInFuseki = Integer.parseInt(sln.getLiteral("jobIdInFuseki").toString());
				jobIdsOfJobsAppliedIn.add(jobIdInFuseki);
			}
		}
		qe3.close();
		System.out.println("jobIdsOfJobsAppliedIn before returning is ->>>>>>>>"+
				jobIdsOfJobsAppliedIn);
		return jobIdsOfJobsAppliedIn;
		
	}

	public List<JobOffer> findAllJobsPostedBy(Employer employer, HttpServletRequest request) {
		
		List<JobOffer> jobOffers = new ArrayList<JobOffer>();
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query",
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + "PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n"
						+ "SELECT * \r\n" + "WHERE {\r\n" + "?jobId dh:jobOfferId ?jobIdInFuseki .\r\n"
						+ "?jobId foaf:mbox ?employerEmail .\r\n"
						+ "?jobId dh:appointmentDescription ?appointmentDescription .\r\n"
						+ "?jobId dh:jobOpenings ?jobOpenings .\r\n"
						+ "?jobId dh:jobOpeningsAlreadyFilled ?jobOpeningsAlreadyFilled .\r\n"
						+ "?jobId dh:jobStarts ?jobStarts .\r\n" + "?jobId dh:jobEnds ?jobEnds . \r\n"
						+ "?jobId dh:ratingRequired ?ratingRequired .\r\n" + "?jobId dh:paymentType ?paymentType .\r\n"
						+ "?jobId dh:paymentMode ?paymentMode .\r\n" + "}");
		
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext()) {
			System.out.println(">>>>>>>>>>>>>>> QE3 Hurray : Going to add jobs to the Arraylist");
			QuerySolution sln = results3.nextSolution();
			System.out.println(">>>>>>>>>>>>>>> 1");
			Integer jobIdInFuseki = Integer.parseInt(sln.getLiteral("jobIdInFuseki").toString());
			System.out.println(">>>>>>>>>>>>>>> 2");
			String employerEmail = sln.getLiteral("employerEmail").toString();
			System.out.println(">>>>>>>>>>>>>>> 3");
			String appointmentDescription = sln.getLiteral("appointmentDescription").toString();
			Integer jobOpenings = Integer.parseInt(sln.getLiteral("jobOpenings").toString());
			Integer jobOpeningsAlreadyFilled = Integer.parseInt(sln.getLiteral("jobOpeningsAlreadyFilled").toString());
			// String dateOfBirth =
			// sln.getLiteral("dateOfBirth").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
			// "");
			String jobStarts = sln.getLiteral("jobStarts").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");
			String jobEnds = sln.getLiteral("jobEnds").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");

			// Double latitude = sln.getLiteral("latitude").getDouble();
			// Double longitude = sln.getLiteral("longitude").getDouble();
			Double ratingRequired = sln.getLiteral("ratingRequired").getDouble();
			String paymentType = sln.getLiteral("paymentType").toString();
			String paymentMode = sln.getLiteral("paymentMode").toString();

			JobOffer jobOffer = new JobOffer(jobIdInFuseki, appointmentDescription, jobOpenings,
					jobOpeningsAlreadyFilled, jobStarts, jobEnds, paymentMode, paymentType, employerEmail,
					ratingRequired);

			jobOffers.add(jobOffer);

		}
		qe3.close();

		// Now, jobOffers contain all the jobs inserted in fuseki. 
		// Removing jobs where employer email is not the same as 
		// that of current logged in employer.

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next(); 
			if (!(nextJobOffer.getEmployerEmail().equals(employer.getEmail()))) {
				iterator.remove();
			}

		}
		return jobOffers;
		
	}

	public List<JobOffer> findAllJobsAWorkerHasAppliedIn(String theWorkerEmail, HttpServletRequest request) {
		Worker worker = (Worker) request.getSession().getAttribute("worker");
		System.out.println(
				">>>>>>>>>>>>>>> INSIDE public List<JobOffer> findAllMatchingJobs(String theWorkerEmail) - STARTS ");
		List<JobOffer> jobOffers = new ArrayList<JobOffer>();
		QueryExecution qe3 = QueryExecutionFactory.sparqlService("http://localhost:3030/dh/query",
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + "PREFIX dh:<http://purl.org/dailyhire/0.1/>\r\n"
						+ "SELECT * \r\n" + "WHERE {\r\n" + "?jobId dh:jobOfferId ?jobIdInFuseki .\r\n"
						+ "?jobId foaf:mbox ?employerEmail .\r\n"
						+ "?jobId dh:appointmentDescription ?appointmentDescription .\r\n"
						+ "?jobId dh:jobOpenings ?jobOpenings .\r\n"
						+ "?jobId dh:jobOpeningsAlreadyFilled ?jobOpeningsAlreadyFilled .\r\n"
						+ "?jobId dh:jobStarts ?jobStarts .\r\n" + "?jobId dh:jobEnds ?jobEnds . \r\n"
						+ "?jobId dh:ratingRequired ?ratingRequired .\r\n" + "?jobId dh:paymentType ?paymentType .\r\n"
						+ "?jobId dh:paymentMode ?paymentMode .\r\n" + "}");
		ResultSet results3 = qe3.execSelect();
		while (results3.hasNext()) {
			System.out.println(">>>>>>>>>>>>>>> QE3 Hurray : Going to add jobs to the Arraylist");
			QuerySolution sln = results3.nextSolution();
			System.out.println(">>>>>>>>>>>>>>> 1");
			Integer jobIdInFuseki = Integer.parseInt(sln.getLiteral("jobIdInFuseki").toString());
			System.out.println(">>>>>>>>>>>>>>> 2");
			String employerEmail = sln.getLiteral("employerEmail").toString();
			System.out.println(">>>>>>>>>>>>>>> 3");
			String appointmentDescription = sln.getLiteral("appointmentDescription").toString();
			Integer jobOpenings = Integer.parseInt(sln.getLiteral("jobOpenings").toString());
			Integer jobOpeningsAlreadyFilled = Integer.parseInt(sln.getLiteral("jobOpeningsAlreadyFilled").toString());
			// String dateOfBirth =
			// sln.getLiteral("dateOfBirth").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
			// "");
			String jobStarts = sln.getLiteral("jobStarts").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");
			String jobEnds = sln.getLiteral("jobEnds").toString().replace("^^http://www.w3.org/2001/XMLSchema#date",
					"");

			// Double latitude = sln.getLiteral("latitude").getDouble();
			// Double longitude = sln.getLiteral("longitude").getDouble();
			Double ratingRequired = sln.getLiteral("ratingRequired").getDouble();
			String paymentType = sln.getLiteral("paymentType").toString();
			String paymentMode = sln.getLiteral("paymentMode").toString();

			JobOffer jobOffer = new JobOffer(jobIdInFuseki, appointmentDescription, jobOpenings,
					jobOpeningsAlreadyFilled, jobStarts, jobEnds, paymentMode, paymentType, employerEmail,
					ratingRequired);

			jobOffers.add(jobOffer);

		}
		qe3.close();
		
		
		// Remove Jobs in which worker has not applied
		
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();

			List<Integer> jobsAppliedIn = this.getJobIdsOfJobsAppliedIn(theWorkerEmail);
			if(!jobsAppliedIn.contains(nextJobOffer.getJobIdInFuseki())) {
				iterator.remove();
			}
			
			/*
			 * //List<JobOffer> jobsAppliedIn = worker.getJobsAppliedIn(); for
			 * (Iterator<Integer> jobsAppliedInIterator = jobsAppliedIn.iterator();
			 * jobsAppliedInIterator .hasNext();) { Integer nextJobAppliedIn =
			 * jobsAppliedInIterator.next(); if
			 * (nextJobOffer.getJobIdInFuseki().equals(nextJobAppliedIn)) {
			 * iterator.remove(); } }
			 */
		}
		return jobOffers;

	}

}
