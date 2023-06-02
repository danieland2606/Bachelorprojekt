package com.meowmed.rdapolicy.module;

import java.time.LocalDate;
import java.util.ArrayList;

import com.meowmed.rdapolicy.persistence.CatRepository;
import com.meowmed.rdapolicy.persistence.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.persistence.PolicyRepository;
import com.meowmed.rdapolicy.persistence.entity.CatEntity;
import com.meowmed.rdapolicy.persistence.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.persistence.entity.PolicyEntity;

public class Policysetup {
    	/**
	 * Setup Methode für das erzeugen von Testdaten bzw. füllen der Datenbank
	 */
	public static void setUp(PolicyRepository pRepository, ObjectOfInsuranceRepository oRepository, CatRepository cRepository){
                LocalDate startDate = LocalDate.of(2017, 1, 15);
                LocalDate endDate1 = LocalDate.of(2099, 1, 1);
                LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
                LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
                ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
                ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "Bengal", "Schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
                PolicyEntity policy1 = new PolicyEntity(1 , startDate, endDate1, 50000, 765,cat1,startDate);
                PolicyEntity policy2 = new PolicyEntity(1 ,startDate, endDate1, 50000, 765 ,cat2, startDate);
                oRepository.save(cat1);
                oRepository.save(cat2);
                pRepository.save(policy1);
                pRepository.save(policy2);
                ArrayList<CatEntity> entities = new ArrayList<>();
                entities.add(new CatEntity("siamese", 12, 15, 4, 7, 2, new String[]{"seal","blau","lilac","creme"})) ;
                entities.add(new CatEntity("perser", 12, 16, 4, 7, 3, new String[]{"weiß", "schildpatt","schwarz"}));
                entities.add(new CatEntity("bengal", 12, 16, 4, 6, 4, new String[]{"braun", "schildpatt","marmor"}));
                entities.add(new CatEntity("maine-coon", 12, 15, 5, 10, 2, new String[]{"grau","braun","weiß"}));
                entities.add(new CatEntity("sphynx", 12, 15, 4, 6, 5, new String[]{}));
                entities.add(new CatEntity("scottish-fold", 12, 15, 4, 6, 6, new String[]{}));
                entities.add(new CatEntity("british-shorthair", 12, 15, 4, 6, 0, new String[]{}));
                entities.add(new CatEntity("abyssinian", 12, 15, 3, 5, 4, new String[]{"rot", "schildpatt", "zimt"}));
                entities.add(new CatEntity("ragdoll", 12, 15, 4, 7, 3, new String[]{"blau", "seal", "lilac", "schildpatt"}));
                cRepository.saveAll(entities);
        }
}
