package com.alliance.jumpstart.services;

import java.util.stream.StreamSupport;

import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.responses.AnalyticsDataResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsService {

        private CareersRepository careersRepo;
        private ApplicantsRepository applicantsRepo;

        @Autowired
        public AnalyticsService(CareersRepository careersRepo, ApplicantsRepository applicantsRepo) {
                this.careersRepo = careersRepo;
                this.applicantsRepo = applicantsRepo;
        }

        public AnalyticsDataResponse createAnalyticsResponse() {
                long numberOfViews = StreamSupport.stream(careersRepo.findAll().spliterator(), false)
                                .map(a -> a.getNumberOfViews()).reduce(0l, (acc, val) -> acc + val);

                long numberOfCandidates = StreamSupport.stream(applicantsRepo.findAll().spliterator(), false)
                                .filter(a -> a.getStatus().equals("PENDING") || a.getStatus().equals("NEW")).count();

                long numberOfMoved = StreamSupport.stream(applicantsRepo.findAll().spliterator(), false)
                                .filter(a -> a.getStatus().equals("MOVED")).count();

                long numberOfHired = StreamSupport.stream(applicantsRepo.findAll().spliterator(), false)
                                .filter(a -> a.getStatus().equals("HIRED")).count();

                return new AnalyticsDataResponse(numberOfViews, numberOfCandidates, numberOfMoved, numberOfHired);
        }

}