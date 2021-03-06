package org.ovirt.engine.ui.uicommonweb.models.storage;
import java.util.Collections;
import org.ovirt.engine.core.compat.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.core.common.vdscommands.*;
import org.ovirt.engine.core.common.queries.*;
import org.ovirt.engine.core.common.action.*;
import org.ovirt.engine.ui.frontend.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;
import org.ovirt.engine.core.common.*;

import org.ovirt.engine.ui.uicommonweb.dataprovider.*;
import org.ovirt.engine.ui.uicommonweb.validation.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.core.common.interfaces.*;

import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;

@SuppressWarnings("unused")
public class NewEditStorageModelBehavior extends StorageModelBehavior
{
	@Override
	public void UpdateItemsAvailability()
	{
		super.UpdateItemsAvailability();

		//Allow Data storage type corresponding to the selected data-center type + ISO and Export that are NFS only:
		for (IStorageModel item : Linq.<IStorageModel>Cast(getModel().getItems()))
		{
			Model model = (Model)item;

			storage_pool dataCenter = (storage_pool)getModel().getDataCenter().getSelectedItem();

			if (item.getRole() == StorageDomainType.ISO)
			{
				AsyncDataProvider.GetIsoDomainByDataCenterId(new AsyncQuery(new Object[] { this, item },
		new INewAsyncCallback() {
			@Override
			public void OnSuccess(Object target, Object returnValue) {

					Object[] array = (Object[])target;
					NewEditStorageModelBehavior behavior = (NewEditStorageModelBehavior)array[0];
					IStorageModel storageModelItem = (IStorageModel)array[1];
					behavior.PostUpdateItemsAvailability(behavior, storageModelItem, returnValue == null);

			}
		}), dataCenter.getId());
			}
			else if (item.getRole() == StorageDomainType.ImportExport)
			{
				AsyncDataProvider.GetExportDomainByDataCenterId(new AsyncQuery(new Object[] { this, item },
		new INewAsyncCallback() {
			@Override
			public void OnSuccess(Object target, Object returnValue) {

					Object[] array = (Object[])target;
					NewEditStorageModelBehavior behavior = (NewEditStorageModelBehavior)array[0];
					IStorageModel storageModelItem = (IStorageModel)array[1];
					behavior.PostUpdateItemsAvailability(behavior, storageModelItem, returnValue == null);

			}
		}), dataCenter.getId());
			}
			else
			{
				PostUpdateItemsAvailability(this, item, false);
			}
		}
	}

	public void PostUpdateItemsAvailability(NewEditStorageModelBehavior behavior, IStorageModel item, boolean isNoStorageAttached)
	{
		storage_pool dataCenter = (storage_pool)getModel().getDataCenter().getSelectedItem();
		Model model = (Model)item;

		model.setIsSelectable(dataCenter != null && ((dataCenter.getId().equals(StorageModel.UnassignedDataCenterId) && item.getRole() == StorageDomainType.Data) || (!dataCenter.getId().equals(StorageModel.UnassignedDataCenterId) && ((item.getRole() == StorageDomainType.Data && item.getType() == dataCenter.getstorage_pool_type()) || (item.getRole() == StorageDomainType.ImportExport && item.getType() == StorageType.NFS && dataCenter.getstatus() != StoragePoolStatus.Uninitialized && isNoStorageAttached) || item.getRole() == StorageDomainType.ISO && item.getType() == StorageType.NFS && dataCenter.getstatus() != StoragePoolStatus.Uninitialized && isNoStorageAttached)) || (getModel().getStorage() != null && item.getType() == getModel().getStorage().getstorage_type())));

		behavior.OnStorageModelUpdated(item);
	}
}