/** Module definition for the functions module */
module appfuncs {
  // Exports the package appfuncs.simplefuncs
  exports appfuncs.simplefuncs to appstart;

  // Requires appsupport and makes it transitive
  requires transitive appsupport;
}