/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs492.obsecurefinal.algorithm;

import cs492.obsecurefinal.common.EntityTypes;
import cs492.obsecurefinal.common.NamedEntity;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 *
 * @author JOEL
 */
public class LocationExtractorStrategy extends EntityExtractorStrategy
{
    public LocationExtractorStrategy(String[] sentence)
    {
        super(sentence, EntityTypes.LOCATION);
    }

    @Override
    public List<NamedEntity> getEntities() 
    {
        List<NamedEntity> locEntities = new Vector<NamedEntity>();
        
        try
        {
            InputStream modelFile = new FileInputStream("models/opennlp/ner/en-ner-location.bin");
            TokenNameFinderModel tnf = new TokenNameFinderModel(modelFile);
            NameFinderME nf = new NameFinderME(tnf);
            Span spans[] = nf.find(sentence);
            String entities[] = Span.spansToStrings(spans, sentence);
            
            // Add all identified location entities to the list
            for(String ent:entities)
                locEntities.add(new NamedEntity(ent, type));
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.out);
        }
        
        return locEntities;
    }
}