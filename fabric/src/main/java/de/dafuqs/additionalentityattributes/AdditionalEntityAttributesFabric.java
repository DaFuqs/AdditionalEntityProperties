package de.dafuqs.additionalentityattributes;

import net.fabricmc.api.ModInitializer;

public class AdditionalEntityAttributesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AdditionalEntityAttributes.init();
    }
}
