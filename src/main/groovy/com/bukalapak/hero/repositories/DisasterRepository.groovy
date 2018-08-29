package com.bukalapak.hero.repositories

import com.bukalapak.hero.models.Disaster
import org.springframework.data.repository.PagingAndSortingRepository

// we may need paging and sorting
interface DisasterRepository extends PagingAndSortingRepository<Disaster, Long> {}