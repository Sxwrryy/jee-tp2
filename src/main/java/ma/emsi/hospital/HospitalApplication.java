package ma.emsi.hospital;

import ma.emsi.hospital.entities.*;
import ma.emsi.hospital.repositories.MedecinRepository;
import ma.emsi.hospital.repositories.PatientRepository;
import ma.emsi.hospital.repositories.RendezVousRepository;
import ma.emsi.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication

public class HospitalApplication {

    private IHospitalService hospitalService;
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private RendezVousRepository rendezVousRepository;

    public static void main(String[] args) {

		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IHospitalService hospitalService,
							PatientRepository patientRepository,
							MedecinRepository medecinRepository,
							RendezVousRepository rendezVousRepository){
        this.hospitalService = hospitalService;
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        return  args -> {
			Stream.of("Mohamed", "Hassan", "Najat")
					.forEach(name->
			{
				Patient patient=new Patient();
				patient.setNom("name");
				patient.setDatedeNaissance(new Date());
				patient.setMalade(false);
				hospitalService.savePatient(patient);
			});

			Stream.of("Ayman", "Hanane", "Yasmine")
					.forEach(name->
					{
						Medecin medecin=new Medecin();
						medecin.setNom("name");
						medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
						medecin.setEmail(name+"@gmail.com");
						hospitalService.saveMedecin(medecin);
					});

			Patient patient=patientRepository.findById(1L).orElse(null);
			Patient patient1=patientRepository.findByNom("Mohamed");

			Medecin medecin= medecinRepository.findByNom("yasmine");

			RendezVous rendezVous=new RendezVous();
			rendezVous.setDate(new Date());
			rendezVous.setStatus(StatusRDV.PENDING);
			rendezVous.setMedecin(medecin);
			rendezVous.setPatient(patient);
			RendezVous saveDRVD = hospitalService.saveRendezVous(rendezVous);
			System.out.println(saveDRVD.getId());

			RendezVous rendezVous1= rendezVousRepository.findAll().get(0);
			Consultation consultation= new Consultation();
			consultation.setDateConsultation(new Date());
			consultation.setRendezVous(rendezVous1);
			consultation.setRapport("Rapport de la consultation");
			hospitalService.saveConsultation(consultation);



		};

	}





}
