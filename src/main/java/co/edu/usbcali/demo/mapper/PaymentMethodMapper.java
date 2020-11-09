package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.dto.PaymentMethodDTO;


/**
* @author Zathura Code Generator Version 9.0 http://zathuracode.org/
* www.zathuracode.org
*
* Mapper Build with MapStruct https://mapstruct.org/
* MapStruct is a code generator that greatly simplifies the
* implementation of mappings between Java bean type
* based on a convention over configuration approach.
*/
@Mapper
public interface PaymentMethodMapper {
    public PaymentMethodDTO paymentMethodToPaymentMethodDTO(
        PaymentMethod paymentMethod);

    public PaymentMethod paymentMethodDTOToPaymentMethod(
        PaymentMethodDTO paymentMethodDTO);

    public List<PaymentMethodDTO> listPaymentMethodToListPaymentMethodDTO(
        List<PaymentMethod> paymentMethods);

    public List<PaymentMethod> listPaymentMethodDTOToListPaymentMethod(
        List<PaymentMethodDTO> paymentMethodDTOs);
}
