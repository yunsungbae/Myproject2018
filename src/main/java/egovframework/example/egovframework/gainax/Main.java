package egovframework.example.egovframework.gainax;

import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@Controller
public class Main {
    @RequestMapping(value = "/index.do")
    public String selectIndex(final HttpServletRequest request, ModelMap model) throws Exception {
//        File file = new File("example.shp");
//        Map<String, Object> map = new HashMap<>();
//        map.put("url", file.toURI().toURL());
//
//        DataStore dataStore = DataStoreFinder.getDataStore(map);
//        String typeName = dataStore.getTypeNames()[0];
//
//        FeatureSource<SimpleFeatureType, SimpleFeature> source =
//                dataStore.getFeatureSource(typeName);
//        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
//
//        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
//        try (FeatureIterator<SimpleFeature> features = collection.features()) {
//            while (features.hasNext()) {
//                SimpleFeature feature = features.next();
//                System.out.print(feature.getID());
//                System.out.print(": ");
//                System.out.println(feature.getDefaultGeometryProperty().getValue());
//            }
//        }

        return "Main/index";
    }
    @RequestMapping(value = "/shpSave.do")
    public String ShpSave(final HttpServletRequest request, ModelMap model) throws Exception {
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

//
//        final Map<String, MultipartFile> files = multiRequest.getfile();
//        String realFolder = "d:/upload2/";
//        String originFileName  = files.get(0).getOriginalFilename();
//
//        String saveFileName = originFileName;
//
//        String savePath = realFolder + saveFileName;
//
//        long fileSize = files.get(0).getSize(); // 파일 사이즈



        files.get(0).transferTo(new File(savePath)); // 파일 저장

        File file = new File(savePath);

        Map<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];

        FeatureSource<SimpleFeatureType, SimpleFeature> source =
                dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        try (FeatureIterator<SimpleFeature> features = collection.features()) {
            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                System.out.print(feature.getID());
                System.out.print(": ");
                System.out.println(feature.getDefaultGeometryProperty().getValue());

            }
        }

        return "Main/index";
    }
}

