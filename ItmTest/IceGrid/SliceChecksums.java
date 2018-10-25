// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SliceChecksums.java

package IceGrid;

import java.util.*;

public class SliceChecksums
{

	public static final Map checksums;

	public SliceChecksums()
	{
	}

	static 
	{
		Map map = new HashMap();
		map.put("::IceGrid::AccessDeniedException", "e39e5ad60577c1e7b52e190e1d906b");
		map.put("::IceGrid::AdapterDescriptor", "4ae12581eab9d8ecba56534d28960f0");
		map.put("::IceGrid::AdapterDescriptorSeq", "61bb9118038552b5e80bf14cf41719c");
		map.put("::IceGrid::AdapterDynamicInfo", "b371e9a58f115e6ebfbcda735fee57f7");
		map.put("::IceGrid::AdapterDynamicInfoSeq", "54465843167a2f93fa96d13b7f41ea32");
		map.put("::IceGrid::AdapterInfo", "a22de437e0d82d91cca7d476992b2a43");
		map.put("::IceGrid::AdapterInfoSeq", "9fdbbb3c2d938b4e5f3bf5a21f234147");
		map.put("::IceGrid::AdapterNotExistException", "cee552cb69227f723030cd78b0cccc97");
		map.put("::IceGrid::AdapterObserver", "7f4ed59e236da9d6c35ad7e6ad9cb0");
		map.put("::IceGrid::AdaptiveLoadBalancingPolicy", "eae551a45bf88ecdfdcbd169e3502816");
		map.put("::IceGrid::Admin", "999e7e79aec2733439dcf76e43485640");
		map.put("::IceGrid::AdminSession", "ca6f21e8ff4210158f382cdbc66c2566");
		map.put("::IceGrid::AllocationException", "ea85a8e5e5f281709bf6aa88d742");
		map.put("::IceGrid::AllocationTimeoutException", "6695f5713499ac6de0626277e167f553");
		map.put("::IceGrid::ApplicationDescriptor", "fc17fb9c4c7fc8f17ad10bc5da634a0");
		map.put("::IceGrid::ApplicationDescriptorSeq", "b56d6d3091e8c0199e924bbdc074");
		map.put("::IceGrid::ApplicationInfo", "44ab5928481a1441216f93965f9e6c5");
		map.put("::IceGrid::ApplicationInfoSeq", "dc7429d6b923c3e66eea573eccc1598");
		map.put("::IceGrid::ApplicationNotExistException", "93fdaabe25dcf75485ffd4972223610");
		map.put("::IceGrid::ApplicationObserver", "2862cdcba54714282f68b13a8fb4ae");
		map.put("::IceGrid::ApplicationUpdateDescriptor", "9aef62072a0ecc3ee4be33bc46e0da");
		map.put("::IceGrid::ApplicationUpdateInfo", "c21c8cfe85e332fd9ad194e611bc6b7f");
		map.put("::IceGrid::BadSignalException", "13e67e2d3f46a84aa73fd56d5812caf1");
		map.put("::IceGrid::BoxedDistributionDescriptor", "bab8796f5dc33ebe6955d4bb3219c5e9");
		map.put("::IceGrid::BoxedString", "f6bfc069c5150c34e14331c921218d7");
		map.put("::IceGrid::CommunicatorDescriptor", "b7cdae49f8df0d1d93afb857875ec15");
		map.put("::IceGrid::DbEnvDescriptor", "19c130dac4bf7fa2f82375a85e5f421");
		map.put("::IceGrid::DbEnvDescriptorSeq", "d0e45f67b942541727ae69d6cda2fdd8");
		map.put("::IceGrid::DeploymentException", "e316fdba8e93ef72d58bd61bbfe29e4");
		map.put("::IceGrid::DistributionDescriptor", "109eee8e2dc57e518243806796d756");
		map.put("::IceGrid::FileIterator", "54341a38932f89d199f28ffc4712c7");
		map.put("::IceGrid::FileNotAvailableException", "a3e88ae3be93ecd4c82797ad26d6076");
		map.put("::IceGrid::FileParser", "b847ccf3e3db7cbba649ec7cc464faf");
		map.put("::IceGrid::IceBoxDescriptor", "814eec3d42ab727f75f7b183e1b02c38");
		map.put("::IceGrid::LoadBalancingPolicy", "dfbd5166bbdcac620f2d7f5de185afe");
		map.put("::IceGrid::LoadInfo", "c28c339f5af52a46ac64c33864ae6");
		map.put("::IceGrid::LoadSample", "ec48c06fa099138a5fbbce121a9a290");
		map.put("::IceGrid::Locator", "816e9d7a3cb39b8c80fe342e7f18ae");
		map.put("::IceGrid::NodeDescriptor", "be38d2d0b946fea6266f7a97d493d4");
		map.put("::IceGrid::NodeDescriptorDict", "600e78031867992f2fbd18719cb494");
		map.put("::IceGrid::NodeDynamicInfo", "3ad52341f32973212d26a9a6dda08b");
		map.put("::IceGrid::NodeDynamicInfoSeq", "f61633c5e3992f718dba78b7f165c2");
		map.put("::IceGrid::NodeInfo", "f348b389deb653ac28b2b991e23d63b9");
		map.put("::IceGrid::NodeNotExistException", "f07ddace1aa3cb1bbed37c3fbf862dff");
		map.put("::IceGrid::NodeObserver", "e06c1ad6807d2876de9e818855a65738");
		map.put("::IceGrid::NodeUnreachableException", "8f894a5022704f4dde30bb2a3ea326f9");
		map.put("::IceGrid::NodeUpdateDescriptor", "d1c0a29ce34753b44e54285c49c9780");
		map.put("::IceGrid::NodeUpdateDescriptorSeq", "3416e1746e2acedfb8192d9d83d9dc3");
		map.put("::IceGrid::ObjectDescriptor", "7df8af93b2bd6918d632115031afef9f");
		map.put("::IceGrid::ObjectDescriptorSeq", "57236a6ef224f825849907a344412bb");
		map.put("::IceGrid::ObjectExistsException", "833f69d3ebc872974a9f096352d2ddb");
		map.put("::IceGrid::ObjectInfo", "6c8a382c348df5cbda50e58d87189e33");
		map.put("::IceGrid::ObjectInfoSeq", "1491c01cb93b575c602baed26ed0f989");
		map.put("::IceGrid::ObjectNotRegisteredException", "cb181c92b4dfb6e6b97f4ca806899e7");
		map.put("::IceGrid::ObjectObserver", "5364683a872f127e46cc5e215d98c3c");
		map.put("::IceGrid::ObserverAlreadyRegisteredException", "e1267578f9666e2bda9952d7106fd12c");
		map.put("::IceGrid::OrderedLoadBalancingPolicy", "bef5dacddeeae0e0b58945adaea2121");
		map.put("::IceGrid::ParseException", "dec9aacba8b3ba76afc5de1cc3489598");
		map.put("::IceGrid::PatchException", "c28994d76c834b99b94cf4535a13d3");
		map.put("::IceGrid::PermissionDeniedException", "27def8d4569ab203b629b9162d530ba");
		map.put("::IceGrid::PropertyDescriptor", "8b2145a8b1c5c8ffc9eac6a13e731798");
		map.put("::IceGrid::PropertyDescriptorSeq", "5f4143ef7e2c87b63136a3177b7a2830");
		map.put("::IceGrid::PropertySetDescriptor", "d07a6de61ed833b349d869bacb7d857");
		map.put("::IceGrid::PropertySetDescriptorDict", "30fc60d722ab4ba7affa70387730322f");
		map.put("::IceGrid::Query", "39dbe5f62c19aa42c2e0fbaf220b4f1");
		map.put("::IceGrid::RandomLoadBalancingPolicy", "b52a26591c76fe2d6d134d954568c1a");
		map.put("::IceGrid::Registry", "c78158c7cf2552d132645cd358d74c");
		map.put("::IceGrid::RegistryInfo", "60e64fc1e37ce59ecbeed4a0e276ba");
		map.put("::IceGrid::RegistryInfoSeq", "fabb868b9f2164f68bc9eb68240c8a6");
		map.put("::IceGrid::RegistryNotExistException", "9e1c1b717e9c5ef72886f16dbfce56f");
		map.put("::IceGrid::RegistryObserver", "fd83b1558e7c77f2d322b25449518");
		map.put("::IceGrid::RegistryUnreachableException", "514020cac28c588ae487a628e227699");
		map.put("::IceGrid::ReplicaGroupDescriptor", "6e64712fedb23bb2c548916e74620c8");
		map.put("::IceGrid::ReplicaGroupDescriptorSeq", "5a3d3e7b4dc5f21b74f7adb5a6b24ccc");
		map.put("::IceGrid::RoundRobinLoadBalancingPolicy", "d9c7e987c732d89b7aa79621a788fcb4");
		map.put("::IceGrid::ServerDescriptor", "45903227dd1968cedd1811b9d71bc374");
		map.put("::IceGrid::ServerDescriptorSeq", "1bf128cadf1974b22258f66617a1ed");
		map.put("::IceGrid::ServerDynamicInfo", "fd4b9177ca54ae4688b51fa51d6870");
		map.put("::IceGrid::ServerDynamicInfoSeq", "e3fda58997d5cd946e78cae739174cb");
		map.put("::IceGrid::ServerInfo", "7f99dc872345b2c3c741c8b4c23440da");
		map.put("::IceGrid::ServerInstanceDescriptor", "56938d38e0189cdbd57d16e5a6bbc0fb");
		map.put("::IceGrid::ServerInstanceDescriptorSeq", "2a8ae55ccef7917d96691c0a84778dd");
		map.put("::IceGrid::ServerNotExistException", "6df151f3ce87bd522ed095f7ad97a941");
		map.put("::IceGrid::ServerStartException", "ce92acafa218dd1d1e8aafab20d1");
		map.put("::IceGrid::ServerState", "21e8ecba86a4678f3b783de286583093");
		map.put("::IceGrid::ServerStopException", "edb57abb5393b8b31b41f3a8e5bd111");
		map.put("::IceGrid::ServerUnreachableException", "f3233583ef7ad8eac2f961aedafdd64");
		map.put("::IceGrid::ServiceDescriptor", "7c2496565248aa7d9732565ee5fe7c");
		map.put("::IceGrid::ServiceDescriptorSeq", "cc519ed2b7f626b896cdc062823166");
		map.put("::IceGrid::ServiceInstanceDescriptor", "8581f0afc39ae7daab937244b28c1394");
		map.put("::IceGrid::ServiceInstanceDescriptorSeq", "eb22cd2a50e79f648d803c4b54755");
		map.put("::IceGrid::Session", "cf4206d0a8aff6c1b0f2c437f34c5d");
		map.put("::IceGrid::StringObjectProxyDict", "978c325e58cebefb212e5ebde28acdc");
		map.put("::IceGrid::StringStringDict", "87cdc9524ba3964efc9091e5b3346f29");
		map.put("::IceGrid::TemplateDescriptor", "d1229192d114f32db747493becd5765");
		map.put("::IceGrid::TemplateDescriptorDict", "7b9427f03e8ce3b67decd2cc35baa1");
		map.put("::IceGrid::UserAccountMapper", "779fd561878e199444e04cdebaf9ffd4");
		map.put("::IceGrid::UserAccountNotFoundException", "fe2dc4d87f21b9b2cf6f1339d1666281");
		checksums = Collections.unmodifiableMap(map);
	}
}
