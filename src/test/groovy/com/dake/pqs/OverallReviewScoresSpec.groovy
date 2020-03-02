package com.dake.pqs

import com.dake.pqs.model.BnbListing
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class OverallReviewScoresSpec extends Specification {
    def 'filters out all listings with a review_scores_rating lower than reviewScore: #reviewScore'() {
        given:
        BnbListing bnbListing = new BnbListing()
        bnbListing.reviewScoresRating = reviewScore

        when:
        def actualResult = true

        then:
        actualResult == expectedResult

        where:
        reviewScore | expectedResult
        -1          | false
        0           | false
        90          | true
        null        | false
    }

    def getListing(Closure<BnbListing> transform) {
        BnbListing bnbListing = new BnbListing()
        transform(bnbListing)
    }


//lazy generate csv column headers to java variables
//    def row = 'id,listing_url,scrape_id,last_scraped,name,summary,space,description,experiences_offered,neighborhood_overview,notes,transit,access,interaction,house_rules,thumbnail_url,medium_url,picture_url,xl_picture_url,host_id,host_url,host_name,host_since,host_location,host_about,host_response_time,host_response_rate,host_acceptance_rate,host_is_superhost,host_thumbnail_url,host_picture_url,host_neighbourhood,host_listings_count,host_total_listings_count,host_verifications,host_has_profile_pic,host_identity_verified,street,neighbourhood,neighbourhood_cleansed,neighbourhood_group_cleansed,city,state,zipcode,market,smart_location,country_code,country,latitude,longitude,is_location_exact,property_type,room_type,accommodates,bathrooms,bedrooms,beds,bed_type,amenities,square_feet,price,weekly_price,monthly_price,security_deposit,cleaning_fee,guests_included,extra_people,minimum_nights,maximum_nights,minimum_minimum_nights,maximum_minimum_nights,minimum_maximum_nights,maximum_maximum_nights,minimum_nights_avg_ntm,maximum_nights_avg_ntm,calendar_updated,has_availability,availability_30,availability_60,availability_90,availability_365,calendar_last_scraped,number_of_reviews,number_of_reviews_ltm,first_review,last_review,review_scores_rating,review_scores_accuracy,review_scores_cleanliness,review_scores_checkin,review_scores_communication,review_scores_location,review_scores_value,requires_license,license,jurisdiction_names,instant_bookable,is_business_travel_ready,cancellation_policy,require_guest_profile_picture,require_guest_phone_verification,calculated_host_listings_count,calculated_host_listings_count_entire_homes,calculated_host_listings_count_private_rooms,calculated_host_listings_count_shared_rooms,reviews_per_month'
//
//    def myFunc() {
//        row.split(",")
//                .collect{it -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, it)}
//                .collect{it -> "private String " + it + ";"}
//                .each{it -> println it}
//    }
}
