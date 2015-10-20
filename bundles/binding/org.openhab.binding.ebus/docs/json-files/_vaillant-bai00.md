# JSON configuration for __vaillant-bai00_

ID                                | Command                     | Item type | Description                                                
---                               | ---                         | ---       | ---                                                        
boiler.speed_fan                  | boiler.speed_fan            | Number    | Fan speed (?)                                              
boiler.temp_d_flow                | boiler.temp_d_flow          | Number    | Desired flow temperature (°C)                              
boiler.temp_flow.status_flow      | boiler.temp_flow            | Number    | Status flow temperature - {0=ok, 85=circuit, 170=cutoff}   
boiler.temp_flow.temp_flow        | boiler.temp_flow            | Number    | Flow temperature (°C)                                      
boiler.temp_return.status_return  | boiler.temp_return          | Number    | Status return temperature - {0=ok, 85=circuit, 170=cutoff} 
boiler.temp_return.temp_return    | boiler.temp_return          | Number    | Return temperature (°C)                                    
boiler.value_io                   | boiler.value_io             | Number    | Ionization current                                         
dhw.temp_cylinder.status_cylinder | dhw.temp_cylinder           | Number    | Storage temperature status - {0=ok, 85=circuit, 170=cutoff}
dhw.temp_cylinder.temp_cylinder   | dhw.temp_cylinder           | Number    | Storage temperature                                        
heating.level_modulation          | heating.level_modulation    | Number    | Modulation level (%)                                       
heating.level_modulation-kw       | heating.level_modulation-kw | Number    | Modulation level (kW)                                      
heating.pressure.pressure         | heating.pressure            | Number    | System pressure (bar)                                      
heating.pressure.status_pressure  | heating.pressure            | Number    | Status system pressure - {0=ok, 85=circuit, 170=cutoff}    
