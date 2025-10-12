package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.hsn.HsnCodesDto;
import com.coder.springjwt.services.adminServices.hsnService.HsnCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_HSN_CONTROLLER)
public class HsnController {


    @Autowired
    private HsnCodeService hsnCodeService;

    @PostMapping(AdminUrlMappings.SAVE_HSN)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveHsn(@Validated @RequestBody HsnCodesDto hsnCodesDto) {
        return this.hsnCodeService.saveHsn(hsnCodesDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_HSN)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHsnCode(@PathVariable long hsnCodeId ) {
        return this.hsnCodeService.deleteHsnCode(hsnCodeId);
    }

    @GetMapping(AdminUrlMappings.GET_HSN_CODE_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getHsnCodeById(@PathVariable long hsnCodeId ) {
        return this.hsnCodeService.getHsnCodeById(hsnCodeId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_HSN_CODE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateHsnCode(@Validated @RequestBody HsnCodesDto hsnCodesDto ) {
        return this.hsnCodeService.updateHsnCode(hsnCodesDto);
    }

    @PostMapping(AdminUrlMappings.GET_HSN_LIST_BY_PAGINATION)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getHsnListByPagination(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.hsnCodeService.getHsnCodesPagination(page,size);
    }


}
