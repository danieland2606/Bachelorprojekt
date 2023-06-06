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
	public static void setUp(PolicyRepository pRepository, ObjectOfInsuranceRepository oRepository){
                LocalDate startDate = LocalDate.of(2017, 1, 15);
                LocalDate endDate1 = LocalDate.of(2099, 1, 1);
                LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
                LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
                ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "bengal", "braun", birthDate1, false, "anhaenglich", "drinnen", 4);
                cat1.setId(1);
                ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "bengal", "schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
                cat2.setId(2);
                PolicyEntity policy1 = new PolicyEntity(1 , startDate, endDate1, 50000, 45,cat1,startDate);
                policy1.setId(1);
                PolicyEntity policy2 = new PolicyEntity(1 ,startDate, endDate1, 50000, 64 ,cat2, startDate);
                policy2.setId(2);
                oRepository.save(cat1);
                oRepository.save(cat2);
                pRepository.save(policy1);
                pRepository.save(policy2);
        }

        public static void catSetUp(CatRepository cRepository){
                ArrayList<CatEntity> entities = new ArrayList<>();
                entities.add(new CatEntity(1, "siamese", 12, 15, 4, 7, 2, new String[]{"seal","blau","lilac","creme"})) ;
                entities.add(new CatEntity(2, "perser", 12, 16, 4, 7, 3, new String[]{"weiß", "schildpatt","schwarz"}));
                entities.add(new CatEntity(3, "bengal", 12, 16, 4, 6, 4, new String[]{"braun", "schildpatt","marmor"}));
                entities.add(new CatEntity(4, "maine-coon", 12, 15, 5, 10, 2, new String[]{"grau","braun","weiß"}));
                entities.add(new CatEntity(5, "sphynx", 12, 15, 4, 6, 5, new String[]{}));
                entities.add(new CatEntity(6, "scottish-fold", 12, 15, 4, 6, 6, new String[]{}));
                entities.add(new CatEntity(7, "british-shorthair", 12, 15, 4, 6, 0, new String[]{}));
                entities.add(new CatEntity(8, "abyssinian", 12, 15, 3, 5, 4, new String[]{"rot", "schildpatt", "zimt"}));
                entities.add(new CatEntity(9, "ragdoll", 12, 15, 4, 7, 3, new String[]{"blau", "seal", "lilac", "schildpatt"}));
                cRepository.saveAll(entities);
        }
}
