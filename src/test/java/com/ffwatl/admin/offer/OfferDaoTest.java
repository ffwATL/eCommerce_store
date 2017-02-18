package com.ffwatl.admin.offer;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.dao.OfferCodeDao;
import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferService;
import com.ffwatl.admin.offer.service.type.OfferDiscountType;
import com.ffwatl.admin.offer.service.type.OfferType;
import com.ffwatl.common.FetchMode;
import com.ffwatl.common.rule.Rule;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"/spring/spring-application-context.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/dataset/offer_test.xml")
public class OfferDaoTest {

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "offer_dao")
    private OfferDao offerDao;

    @Resource(name = "offer_service")
    private OfferService offerService;

    @Resource(name = "rulesMap")
    private Map<String, Rule> ruleMap;

    @Resource(name = "codeSpring25")
    private OfferCode offerCode;

    @Resource(name = "offer_code_dao")
    private OfferCodeDao offerCodeDao;

    @Resource(name = "offerForSaveTest")
    private Offer offerForSaveTest;

    @Test
    public void offerDaoEagerModeTest(){
        Offer offer = offerDao.findOfferById(1, FetchMode.FETCHED);
        List<Offer> offerList = offerDao.findAllOffers(FetchMode.FETCHED);

        assertNotNull(offerList);
        assertEquals(2, offerList.size());

        assertNotNull(offer);

        // test that correct basic information for the Offer with id=1 were fetched
        assertNotNull(offer.getType());
        assertEquals(offer.getType(), OfferType.ORDER);

        assertNotNull(offer.getDiscountType());
        assertEquals(offer.getDiscountType(), OfferDiscountType.PERCENT_OFF);

        assertNotNull(offer.getName());
        assertEquals(offer.getName(), "Spring sale 30%");

        assertNotNull(offer.getStartDate());
        assertNotNull(offer.getEndDate());
        LocalDate startDate = offer.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = offer.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        assertFalse(startDate.isAfter(endDate));
        assertEquals(LocalDate.ofYearDay(1970, 1).getYear(), startDate.getYear());

        assertTrue(offer.isValidOnSale());

        assertEquals(1, offer.getMaxUsesPerCustomer());
        assertFalse(offer.isCombinableWithOtherOffers());

        assertEquals(30000, offer.getQualifyingItemSubTotal());
        assertFalse(offer.isTotalitarianOffer());
        assertFalse(offer.isAutomaticallyAdded());
        assertEquals(offer.getOfferCurrency(), Currency.UAH);

        assertNotNull(offer.getOfferCodes());
        assertEquals(3, offer.getOfferCodes().size());
        //

        offer.setMatchRules(ruleMap);

        assertNotNull(offer);
        assertNotNull(offer.getOfferCodes());
        assertTrue(offer.getOfferCodes().size() > 0);

        offer = offerDao.create();
        assertNotNull(offer);

        assertNull(offer.getOfferCodes());
        assertNull(offer.getDiscountType());
        assertNull(offer.getEndDate());
        assertNull(offer.getMatchRules());
        assertNull(offer.getType());

        System.err.println(offer);
    }

    @Test
    public void testOfferDaoCreateMethods() throws Exception {
        assertNotNull(offerDao.create());
        assertNotNull(offerDao.createCandidateFulfillmentGroupOffer());
        assertNotNull(offerDao.createCandidateItemOffer());
        assertNotNull(offerDao.createCandidateOrderOffer());
        assertNotNull(offerDao.createFulfillmentGroupAdjustment());
        assertNotNull(offerDao.createOrderItemAdjustment());
        assertNotNull(offerDao.createOrderAdjustment());
        assertNotNull(offerDao.createOrderItemPriceDetailAdjustment());
    }

    @Test
    public void testOfferDaoSaveDelete() throws Exception {
        Offer offer = offerDao.findOfferById(1, FetchMode.LAZY);
        assertNotNull(offer);

        offerDao.delete(offer);
        offer = offerDao.findOfferById(1, FetchMode.LAZY);
        assertNull(offer);
        assertNotNull(offerForSaveTest);

        System.err.println(offerDao.save(offerForSaveTest));
        assertNotNull(offerDao);

    }

    @Test
    public void testFindOffersByAutomaticDeliveryType() throws Exception {
        List<Offer> list = offerDao.findOffersByAutomaticDeliveryType(FetchMode.FETCHED);
        logger.info(list);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertTrue(list.get(0).isAutomaticallyAdded());

    }

    @Test
    public void offerServiceBasicFunctionalityTest() throws Exception {
        OfferCode offerCode = offerService.findOfferCodeById(1);

        assertNotNull(offerCode);
        assertFalse(offerCode.isActive());
        assertNotNull(offerCode.getOfferCode());
        assertEquals(offerCode.getOfferCode(), "SPRING30");

        Offer offer = offerCode.getOffer();

        assertNotNull(offer);
        logger.info("offerCode.getOffer(): " + offer);

        assertNotNull(offerService.lookupOfferByCode("SPRING30", FetchMode.FETCHED));
        assertNotNull(offerService.lookupOfferCodeByCode("SPRING30"));

        OfferCode codeSpring25 = offerService.saveOfferCode(offerCode);

        assertNotNull(codeSpring25);
        logger.info("*****codeSpring25: " + codeSpring25);
    }

    @Test
    public void testOfferCodeDaoCrud() throws Exception {
        OfferCode offerCode = offerCodeDao.findOfferCodeById(1);

        assertNotNull(offerCode);
        offerCodeDao.delete(offerCode);
        offerCode = offerCodeDao.findOfferCodeById(1);

        assertNull(offerCode);

        assertNotNull(offerCodeDao.create());
    }
}