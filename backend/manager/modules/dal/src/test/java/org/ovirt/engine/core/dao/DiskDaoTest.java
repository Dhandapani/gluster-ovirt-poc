package org.ovirt.engine.core.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ovirt.engine.core.common.businessentities.Disk;
import org.ovirt.engine.core.common.businessentities.DiskInterface;
import org.ovirt.engine.core.common.businessentities.DiskType;
import org.ovirt.engine.core.common.businessentities.PropagateErrors;
import org.ovirt.engine.core.compat.Guid;

/**
 * Unit tests to validate {@link DiskDao}.
 */
public class DiskDaoTest extends BaseGenericDaoTestCase<Guid, Disk, DiskDao> {

    private static final Guid EXISTING_DISK_ID = new Guid("1b26a52b-b60f-44cb-9f46-3ef333b04a34");
    private static final int TOTAL_DISKS = 1;

    @Override
    protected Guid generateNonExistingId() {
        return Guid.NewGuid();
    }

    @Override
    protected int getEneitiesTotalCount() {
        return TOTAL_DISKS;
    }

    @Override
    protected Disk generateNewEntity() {
        return new Disk(Guid.NewGuid(),
                1,
                DiskType.Data,
                DiskInterface.SCSI,
                true,
                PropagateErrors.Off);
    }

    @Override
    protected void updateExistingEntity() {
        existingEntity.setDiskInterface(DiskInterface.IDE);
    }

    @Override
    protected DiskDao prepareDao() {
        return prepareDAO(dbFacade.getDiskDao());
    }

    @Override
    protected Guid getExistingEntityId() {
        return EXISTING_DISK_ID;
    }

    @Test
    public void existsForExistingDisk() throws Exception {
        assertTrue(dao.exists(EXISTING_DISK_ID));
    }

    @Test
    public void existsForNonExistingDisk() throws Exception {
        assertFalse(dao.exists(new Guid()));
    }
}
