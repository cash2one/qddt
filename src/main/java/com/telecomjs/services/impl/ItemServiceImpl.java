package com.telecomjs.services.impl;

import com.telecomjs.beans.AssessmentItem;
import com.telecomjs.beans.ItemCategory;
import com.telecomjs.beans.StaffAssessmentItem;
import com.telecomjs.datagrid.OperationsRoleHelper;
import com.telecomjs.mappers.AssessmentItemMapper;
import com.telecomjs.mappers.ItemCategoryMapper;
import com.telecomjs.mappers.StaffAssessmentItemMapper;
import com.telecomjs.services.intf.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zark on 17/3/21.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    @Autowired
    AssessmentItemMapper assessmentItemMapper;

    @Autowired
    StaffAssessmentItemMapper staffAssessmentItemMapper;

    @Override
    public int getItemCategorySeq() {
        return itemCategoryMapper.getPrimaryKey();
    }

    @Override
    public int saveCategory(ItemCategory category) {
        if (itemCategoryMapper.updateByPrimaryKey(category)>0)
            return category.getCategoryId();
        else
            return itemCategoryMapper.insert(category);
    }

    @Override
    public List<ItemCategory> findAllCategories() {
        return itemCategoryMapper.findAll();
    }

    @Override
    public List<AssessmentItem> findAllItemsByRole(OperationsRoleHelper.RoleType roleType) {
        String type = roleType.getName();
        return assessmentItemMapper.findItemsByType(type);
    }

    @Override
    public int getAssessmentItemSeq() {
        return itemCategoryMapper.getPrimaryKey();
    }

    @Override
    public int saveItem(AssessmentItem item) {
        if ( assessmentItemMapper.updateByPrimaryKey(item)>0)
            return item.getItemId();
        else
            return assessmentItemMapper.insert(item);
    }

    @Override
    public List<ItemCategory> findAllCategoriesByRole(OperationsRoleHelper.RoleType roleType) {
        return itemCategoryMapper.findAllCategoriesByRole(roleType.getName());
    }

    @Override
    public Integer getStaffItemSeq() {
        return staffAssessmentItemMapper.getPrimaryKey();
    }

    @Override
    public int saveStaffItem(List<StaffAssessmentItem> staffItems) {
        if (staffItems.size()>0) {
            staffAssessmentItemMapper.deleteByStaffAssessmentId(staffItems.get(0).getStaffAssessmentId());
            for (StaffAssessmentItem item : staffItems){
                staffAssessmentItemMapper.insert(item);
            }
            return staffItems.get(0).getStaffAssessmentId();
        }
        return 0;
    }

    @Override
    public List<StaffAssessmentItem> findALlStaffItemsByStaffAssessmentId(int staffAssessmentId) {
        return staffAssessmentItemMapper.findItemsByStaffAssessmentId( staffAssessmentId);
    }
}
