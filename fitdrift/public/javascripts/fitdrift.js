function loadGeoJsonString(geoString) {
  var geojson = JSON.parse(geoString);
  map.data.addGeoJson(geojson);
  zoom(map);
}


/**
 * Update a map's viewport to fit each geometry in a dataset
 * param {google.maps.Map} map The map to adjust
 */
function zoom(map) {
  var bounds = new google.maps.LatLngBounds();
  map.data.forEach(function(feature) {
    processPoints(feature.getGeometry(), bounds.extend, bounds);
  });
  map.fitBounds(bounds);
}


/**
 * Process each point in a Geometry, regardless of how deep the points may lie.
 * param {google.maps.Data.Geometry} geometry The structure to process
 * param {function(google.maps.LatLng)} callback A function to call on each
 *     LatLng point encountered (e.g. Array.push)
 * param {Object} thisArg The value of 'this' as provided to 'callback' (e.g.
 *     myArray)
 */
function processPoints(geometry, callback, thisArg) {
  if (geometry instanceof google.maps.LatLng) {
    callback.call(thisArg, geometry);
  } else if (geometry instanceof google.maps.Data.Point) {
     callback.call(thisArg, geometry.get());
  } else {
    geometry.getArray().forEach(function(g) {
      processPoints(g, callback, thisArg);
    });
  }
}
