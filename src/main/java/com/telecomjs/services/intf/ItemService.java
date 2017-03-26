package com.telecomjs.services.intf;

import com.telecomjs.beans.AssessmentItem;
import com.telecomjs.beans.ItemCategory;
import com.telecomjs.beans.StaffAssessmentItem;
import com.telecomjs.datagrid.OperationsRoleHelper;

import java.util.List;

/**
 * Created by zark on 17/3/21.
 */
public interface ItemService {
    int getItemCategorySeq();

    int saveCategory(ItemCategory category);

    List<ItemCategory> findAllCategories();

    List<AssessmentItem> findAllItemsByRole(OperationsRoleHelper.RoleType roleType);

    int getAssessmentItemSeq();

    int saveItem(AssessmentItem item);

    List<ItemCategory> findAllCategoriesByRole(OperationsRoleHelper.RoleType roleType);

    Integer getStaffItemSeq();

    int saveStaffItem(List<StaffAssessmentItem> staffItems);

    List<StaffAssessmentItem> findALlStaffItemsByStaffAssessmentId(int staffAssessmentId);
}
