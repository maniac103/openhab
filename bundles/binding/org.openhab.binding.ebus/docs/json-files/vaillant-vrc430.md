# JSON configuration for _vaillant-vrc430_

ID                                   | Command                             | Item type | Description                                                             
---                                  | ---                                 | ---       | ---                                                                     
controller.temp_outside.status       | controller.temp_outside             | Number    | Outside temperature status - {0=Kk, 85=Circuit, 170=Cutoff}             
controller.temp_outside.temp_outside | controller.temp_outside             | Number    | Outside temperature                                                     
controller.temp_room.status          | controller.temp_room                | Number    | Room temperature status - {0=Ok, 85=Circuit, 170=Cutoff}                
controller.temp_room.temp_room       | controller.temp_room                | Number    | Room temperature                                                        
controller.temp_room_disp            | controller.temp_room_disp           | Number    | Room temperature                                                        
dhw.dhw_circuit_program              | dhw.dhw_circuit_program             | Number    | DHW Operation mode - {0=Off, 1=Manual, 2=Auto}                          
dhw.set_dhw_circuit_program          | dhw.set_dhw_circuit_program         | Number    | DHW Operation mode - {0=Off, 1=Manual, 2=Auto}                          
dhw.set_temp_d_dhw                   | dhw.set_temp_d_dhw                  | Number    | DHW setpoint                                                            
dhw.temp_d_actual_dhw                | dhw.temp_d_actual_dhw               | Number    | DHW actual desired temperature                                          
dhw.temp_d_dhw                       | dhw.temp_d_dhw                      | Number    | DHW setpoint                                                            
heating.heating_circuit_program      | heating.heating_circuit_program     | Number    | HC1 Operation mode - {0=Off, 1=Manual, 2=Auto, 3=Day, 4=Night, 5=Summer}
heating.set_heating_circuit_program  | heating.set_heating_circuit_program | Number    | HC1 Operation - {0=Off, 1=Manual, 2=Auto, 3=Day, 4=Night, 5=Summer}     
heating.set_temp_d_day               | heating.set_temp_d_day              | Number    | HC1 Day setpoint                                                        
heating.set_temp_d_night             | heating.set_temp_d_night            | Number    | HC1 Night setpoint                                                      
heating.set_temp_hcurve              | heating.set_temp_hcurve             | Number    | HC1 Heating curve                                                       
heating.temp_d_day                   | heating.temp_d_day                  | Number    | HC1 Day setpoint                                                        
heating.temp_d_night                 | heating.temp_d_night                | Number    | HC1 Night setpoint                                                      
heating.temp_d_room_override         | heating.temp_d_room_override        | Number    | HC1 Manual override setpoint                                            
heating.temp_hcurve                  | heating.temp_hcurve                 | Number    | HC1 Heating curve                                                       
