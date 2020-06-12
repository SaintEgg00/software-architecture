package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Supply;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplyMapper {
    List<Supply> getSupplyList();
}
