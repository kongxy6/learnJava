import java.math.BigDecimal;

public class Test2 {

    static {
        System.out.println("static initial");
    }

    public static void a() {
        System.out.println("static method");
    }

    @org.junit.jupiter.api.Test
    void test() {

        Integer l = 12312;
        System.out.println((float) l);

        Boolean bb = true;
        System.out.println(bb.toString());

        System.out.println((long) 31232132131.3213d);

        String a = "dasdad";
        System.out.println(a.substring(0, 1));
        System.out.println(a.substring(6, 6));

        BigDecimal bigDecimal = new BigDecimal("21431.345");
        System.out.println((long) bigDecimal.doubleValue());

        Integer i = 50423713;
        Object obj = i;
        if (obj instanceof BigDecimal) {
            System.out.println("dsada");
        } else {
            double dd = (double) (Integer) obj;
        }
    }

    @org.junit.jupiter.api.Test
    public void test1() {
        String config = "Building configuration...\n\n\nCurrent configuration : 7045 bytes\n!\n! Last configuration change at 19:54:23 Beijing Sun Nov 17 2019 by hxnmuser\n!\nversion 16.6\nno service pad\nservice tcp-keepalives-in\nservice tcp-keepalives-out\nservice timestamps debug datetime msec localtime show-timezone\nservice timestamps log datetime msec localtime show-timezone\nservice password-encryption\nservice compress-config\nplatform qfp utilization monitor load 80\nno platform punt-keepalive disable-kernel-core\n!\nhostname DGFG-BIGCUM-COP-ISR4451-01_205C07#36-37\n!\nboot-start-marker\nboot-end-marker\n!\n!\nvrf definition Mgmt-intf\n !\n address-family ipv4\n exit-address-family\n !\n address-family ipv6\n exit-address-family\n!\nlogging buffered 20480 informational\nenable secret 5 $1$RhJj$Rxfr9sr6gBuIhuuOUBLUm1\n!\naaa new-model\n!\n!\naaa group server tacacs+ PAICNET\n server-private 172.16.240.13 key 7 01030717580F0902\n server-private 172.16.240.14 key 7 08314D5D0A1D0A1A\n ip vrf forwarding Mgmt-intf\n ip tacacs source-interface GigabitEthernet0\n!\naaa authentication login PAICNET group PAICNET local\naaa authorization exec PAICNET group PAICNET if-authenticated \naaa authorization commands 15 PAICNET group PAICNET if-authenticated \naaa accounting update newinfo\naaa accounting exec PAICNET start-stop group PAICNET\naaa accounting commands 0 PAICNET start-stop group PAICNET\naaa accounting commands 1 PAICNET start-stop group PAICNET\naaa accounting commands 15 PAICNET start-stop group PAICNET\naaa accounting network PAICNET start-stop group tacacs+\n!\n!\n!\n!\n!\n!\naaa session-id common\nclock timezone Beijing 8 0\nno ip source-route\n!\n!\n!\n!\n!\n!\n!\nno ip bootp server\nno ip domain lookup\nip domain name pingan.com.cn\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\nsubscriber templating\n! \n! \n! \n! \nipv6 unicast-routing\n!\n!\nmultilink bundle-name authenticated\n!\n!\n!\n!\ncrypto pki trustpoint TP-self-signed-1343312968\n enrollment selfsigned\n subject-name cn=IOS-Self-Signed-Certificate-1343312968\n revocation-check none\n rsakeypair TP-self-signed-1343312968\n!\n!\ncrypto pki certificate chain TP-self-signed-1343312968\n certificate self-signed 01\n  30820330 30820218 A0030201 02020101 300D0609 2A864886 F70D0101 05050030 \n  31312F30 2D060355 04031326 494F532D 53656C66 2D536967 6E65642D 43657274 \n  69666963 6174652D 31333433 33313239 3638301E 170D3139 30343033 30383130 \n  31345A17 0D323030 31303130 30303030 305A3031 312F302D 06035504 03132649 \n  4F532D53 656C662D 5369676E 65642D43 65727469 66696361 74652D31 33343333 \n  31323936 38308201 22300D06 092A8648 86F70D01 01010500 0382010F 00308201 \n  0A028201 0100CD83 DBF21C51 2A6111B3 4F5BBBCE DE3FB300 0E2CAFB3 C802E5EF \n  70DC71F3 DC826A52 27D96C51 B0BB58D5 FF5CA60B 38294802 0B5AE956 A05C04EC \n  353CA3E8 3A53279F 84B8606B 5264C44E 5AC38865 2CD10B52 46149CBA F5410158 \n  8CC1EF56 E7BC59EF 40E101DF F3593784 52A37F75 D78E7FAF F687EE75 F66D6B63 \n  FBBEAA4E 3FA85AA4 763523BB F167064A 13E6FE38 513CA3A3 3C06EA78 BFE85ADD \n  CBA05021 C6C39448 49F45167 523F5132 DB2F72F2 E1644DF5 AE3646C0 2C5C871A \n  0393DF7D 0A1CC347 71D5CF0E 438EE158 9F02D841 FD91409F 2D0DD68A FFB36E3F \n  BFA7FE19 1C0A573A 6BA67D01 2E4E4527 373203DC 62AEAFED 21517DD6 FC7027F4 \n  359B7CD4 5D130203 010001A3 53305130 0F060355 1D130101 FF040530 030101FF \n  301F0603 551D2304 18301680 141AC439 5CDDE4C8 411950D1 2375A9B2 6EE75C4D \n  65301D06 03551D0E 04160414 1AC4395C DDE4C841 1950D123 75A9B26E E75C4D65 \n  300D0609 2A864886 F70D0101 05050003 82010100 7DEDC780 F8FD4C66 4B308495 \n  0752D400 EC18F005 FDF11680 BA8C346F FBD430EE 3C08001A C6BE37B4 08E3D094 \n  F5ECA213 821A444C 8941B6D0 FA5DD7CD 8E386C60 8EEDE402 AA33F4D3 B18FDCDD \n  C4D8F48B 67349019 9E672BCE 077E2773 A07D7B37 3116770F F3A4FF57 5B869B0A \n  9BC10FEA 0A3347CF 28A870D7 B991FD6F 4EA23FDE FA6053AA 87ACFDA8 D79F0011 \n  02AC303B F5AF54D2 784669D8 BCA939D3 C961FC9C 8D4B8C6C 7988A671 769B9E53 \n  185E78A6 D71F7906 EABF60F4 301212EC AC76D9AF 65B8FAFF F47A75AA 04164A82 \n  AD2367D9 BDFC732E 08FC8F51 E74BA37B 9B164BCD BD8BA4C8 B9BFA52F 9EFABB86 \n  F14B6D2D A9782D42 FB80B4B9 FE29C3F4 10946C0C\n  \tquit\n!\n!\nlicense udi pid ISR4451-X/K9 sn FOC23061FWS\ndiagnostic bootup level minimal\nspanning-tree extend system-id\n!\n!\n!\nusername cisco privilege 15 secret 5 $1$YgS0$ttOr/SaHa7uqvROHEqCOh.\n!\nredundancy\n mode none\n!\n!\n!\n!\n!\n!\n!\n! \n! \n!\n!\ninterface GigabitEthernet0/0/0\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet0/0/1\n description To_DGFG-BIGCUM-EXT-N3K-1_E1/39\n no ip address\n negotiation auto\n!\ninterface GigabitEthernet0/0/2\n description to DGFG-BIGCUM-HQ-N3048-1-205C07_33\n no ip address\n negotiation auto\n!\ninterface GigabitEthernet0/0/3\n description to JIUKUN_ETN0001NP_CTC\n ip address 10.120.254.22 255.255.255.252\n negotiation auto\n!\ninterface GigabitEthernet0/1/0\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet0/1/1\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet0/2/0\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet0/2/1\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/0\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/1\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/2\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/3\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/4\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet1/0/5\n no ip address\n shutdown\n negotiation auto\n!\ninterface GigabitEthernet0\n description To_DGFG-BIG-NM-C2960X-01_G1/0/6 \n vrf forwarding Mgmt-intf\n ip address 172.31.21.163 255.255.255.0\n no ip redirects\n no ip proxy-arp\n negotiation auto\n no ipv6 redirects\n!\nip forward-protocol nd\nno ip http server\nip http authentication local\nno ip http secure-server\nip tftp source-interface GigabitEthernet0\nip route vrf Mgmt-intf 0.0.0.0 0.0.0.0 172.31.21.200\n!\nip ssh version 2\n!\nlogging source-interface GigabitEthernet0 vrf Mgmt-intf\nlogging host 172.16.240.12 vrf Mgmt-intf\nlogging host 26.8.1.22\n!\n!\nsnmp-server community newzqro RO\n!\n!\n!\n!\ncontrol-plane\n!\nbanner login ^CIf you are not authorized to access this private computer system,disconnect new,all activities on this system will be monitored and recorded without prior notification or permission.^C\n!\nline con 0\n exec-timeout 5 0\n logging synchronous\n transport input none\n stopbits 1\nline aux 0\n stopbits 1\nline vty 0 4\n exec-timeout 5 0\n authorization commands 15 PAICNET\n authorization exec PAICNET\n accounting commands 0 PAICNET\n accounting commands 1 PAICNET\n accounting commands 15 PAICNET\n accounting exec PAICNET\n logging synchronous\n login authentication PAICNET\n transport input ssh\nline vty 5 15\n exec-timeout 5 0\n authorization commands 15 PAICNET\n authorization exec PAICNET\n accounting commands 0 PAICNET\n accounting commands 1 PAICNET\n accounting commands 15 PAICNET\n accounting exec PAICNET\n logging synchronous\n login authentication PAICNET\n transport input ssh\n!\nntp source GigabitEthernet0\nntp server vrf Mgmt-intf 172.16.240.16\nntp server vrf Mgmt-intf 172.16.240.17\nwsma agent exec\n!\nwsma agent config\n!\nwsma agent filesys\n!\nwsma agent notify\n!\n!\nend";

        System.out.println(config);


    }

}
