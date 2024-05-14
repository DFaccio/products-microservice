package br.com.productmanagement.util;

import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

    public String generateSku(String category, String brand, String name, String model, String color, String size) {

        String categoryCode = formatAttribute(category, 3);
        String brandCode = formatAttribute(brand, 4);
        String nameCode = formatAttribute(name, 3);
        String modelCode = formatAttribute(model, 3);
        String colorCode = formatAttribute(color, 3);
        String sizeCode = formatAttribute(size, 4);

        return String.format("%s-%s-%s-%s-%s-%s", categoryCode, brandCode, nameCode, modelCode, colorCode, sizeCode);
    }

    private String formatAttribute(String attribute, int size) {
        if (attribute == null) {
            return "000";
        }
        attribute = attribute.replaceAll("[^a-zA-Z0-9]", "").trim();
        attribute = attribute.toUpperCase();
        return attribute.substring(0, Math.min(attribute.length(), size));
    }

}
