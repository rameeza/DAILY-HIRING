package org.dailyhiring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.dailyhiring.dao.EmployerRepository;
import org.dailyhiring.dao.JobOfferRepository;
import org.dailyhiring.dao.WorkerRepository;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferServiceImpl implements JobOfferService {

	private JobOfferRepository jobOfferRepository;
	@Autowired
	private WorkerRepository workerRepository;
	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	public JobOfferServiceImpl(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	@Override
	public JobOffer save(JobOffer jobOffer) {
		JobOffer tempJobOffer = jobOfferRepository.save(jobOffer);
		return tempJobOffer;
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
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (!(nextJobOffer.getFieldOfWork().getName().equals(worker.getSkill().getFieldOfWork().getName()))) {
				iterator.remove();
			}

		}
		System.out.println("After matching field of work -> jobOffers.isEmpty()" + jobOffers.isEmpty());

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
		 */		System.out.println("After matching degree requirement -> jobOffers.isEmpty()" + jobOffers.isEmpty());

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
		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();

			if (nextJobOffer.getValidThrough() != null && !nextJobOffer.getValidThrough().isEmpty()) {

				try {
					Date todaysDate = new Date();
					todaysDate.setHours(0);
					Date validThroughDate = new SimpleDateFormat("yyyy-MM-dd").parse(nextJobOffer.getValidThrough());
					validThroughDate.setHours(1);
					if (validThroughDate.before(todaysDate)) {
						System.out.print("validThroughDate -> " + validThroughDate);
						System.out.println(" | todaysDate -> " + todaysDate);
						iterator.remove();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("After removing where Job validity has passed -> jobOffers.isEmpty()" + jobOffers.isEmpty());

		// Remove Jobs recommendation requirement is not satisfied.
		/*
		 * for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();)
		 * { JobOffer nextJobOffer = iterator.next(); if
		 * (nextJobOffer.getRecommendation() != null) { if
		 * (nextJobOffer.getRecommendation() > worker.getRecommendation()) {
		 * iterator.remove(); } } }
		 */		System.out.println("After removing where recommendation requirement is not satisfied"
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

}
