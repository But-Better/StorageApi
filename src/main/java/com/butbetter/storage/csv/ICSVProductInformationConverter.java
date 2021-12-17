package com.butbetter.storage.csv;

import com.butbetter.storage.model.ProductInformation;
import org.springframework.stereotype.Component;

@Component
public interface ICSVProductInformationConverter extends ICSVConverter<ProductInformation> {}
