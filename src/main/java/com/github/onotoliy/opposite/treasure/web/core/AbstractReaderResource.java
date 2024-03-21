package com.github.onotoliy.opposite.treasure.web.core;

import com.github.onotoliy.opposite.treasure.dto.data.core.SearchParameter;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasAuthor;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasCreationDate;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasName;
import com.github.onotoliy.opposite.treasure.dto.data.core.HasUUID;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import com.github.onotoliy.opposite.treasure.dto.data.page.Meta;
import com.github.onotoliy.opposite.treasure.dto.data.page.Page;
import com.github.onotoliy.opposite.treasure.dto.data.page.Paging;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Базовый WEB сервис чтения данных.
 *
 * @param <E> Объект.
 * @param <P> Поисковые параметры объекта.
 * @author Anatoliy Pokhresnyi
 */
public abstract class AbstractReaderResource<
    E extends HasUUID & HasName & HasCreationDate & HasAuthor,
    P extends SearchParameter>
implements ReaderResource<E, P> {

    @Override
    public E get(final UUID uuid) {
        return null;
    }

    @Override
    public Page<E> getAll(final P parameter) {
        return new Page<>(new Meta(0, new Paging(0, 10)), Collections.emptyList());
    }

//    @Override
//    public Option version() {
//        return null;
//    }
//
//    @Override
//    public Page<E> sync(@QueryParam("version") final long version,
//                        @QueryParam("offset") final int offset,
//                        @QueryParam("numberOfRows") final int numberOfRows) {
//        return null;
//    }

//    @Override
//    public List<Option> getAllOptions() {
//        return Collections.emptyList();
//    }
//

}
