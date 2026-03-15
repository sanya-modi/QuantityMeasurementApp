package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementApp {

        private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementApp.class);

        private final IQuantityMeasurementRepository repository;

        public QuantityMeasurementApp() {
                this.repository = initializeRepository();
        }

        private IQuantityMeasurementRepository initializeRepository() {
                String repositoryType = ApplicationConfig.getProperty("repository.type", "cache");

                if ("database".equalsIgnoreCase(repositoryType)) {
                        logger.info("Initializing database repository");
                        return new QuantityMeasurementDatabaseRepository();
                }

                logger.info("Initializing cache repository");
                return QuantityMeasurementCacheRepository.getInstance();
        }

        public void deleteAllMeasurements() {
                repository.deleteAll();
        }

        public void closeResources() {
                repository.releaseResources();
        }

    public static void main(String[] args) {

                QuantityMeasurementApp app = new QuantityMeasurementApp();

                IQuantityMeasurementRepository repository = app.repository;

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        new QuantityMeasurementController(service);

        logger.info("Quantity Measurement Application UC16 Started");
        logger.info("Repository measurement count: {}", repository.getTotalCount());
        logger.info("Repository pool stats: {}", repository.getPoolStatistics());

        app.closeResources();
    }
}